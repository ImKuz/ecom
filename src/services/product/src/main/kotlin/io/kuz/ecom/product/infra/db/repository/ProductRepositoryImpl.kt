package io.kuz.ecom.product.infra.db.repository

import io.kuz.ecom.common.models.common.PaginationMetaModel
import io.kuz.ecom.common.models.product.ProductAttributeModel
import io.kuz.ecom.common.models.product.ProductModel
import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.*
import io.kuz.ecom.product.infra.db.query.ProductAttributeOptionsQuery
import io.kuz.ecom.product.infra.db.query.ProductQuery
import io.kuz.ecom.product.infra.db.table.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.io.IOException
import kotlin.math.ceil
import kotlin.math.min

@Repository
class ProductRepositoryImpl: ProductRepository {

    override suspend fun getProductById(
        id: Int
    ): ProductModel? = newSuspendedTransaction {
        val productQuery = ProductQuery
            .scopedSelectQuery
            .where { ProductTable.id eq id }
            .singleOrNull() ?: return@newSuspendedTransaction null

        val options = ProductAttributeOptionsQuery.mapProductAttributes(
            ProductAttributeOptionsQuery
                .baseQuery
                .where { ProductAttributeValuesTable.productId eq id }
                .toList()
        )

        return@newSuspendedTransaction ProductQuery.map(
            productQuery,
            options
        )
    }

    override suspend fun getProductList(
        criteria: ProductFetchCriteriaModel,
        page: Int,
        pageSize: Int
    ): ProductFetchResultModel = newSuspendedTransaction {
        val (limit, offset) = toLimitOffset(page, pageSize)
        val criteriaConditions = buildProductCriteriaConditions(criteria).toMutableList()

        val optionsFilterPair = productsOptionsFilter(
            criteria.attributeOptions,
            limit,
            offset,
        )
        val productCountByOptions = optionsFilterPair?.first?.toLong()

        optionsFilterPair?.second?.let {
            criteriaConditions.add(it)
        }

        val whereClause = criteriaConditions
            .reduceOrNull { acc, cond -> acc and cond } ?: Op.TRUE

        val productsCount = ProductQuery
            .baseQuery
            .selectAll()
            .where { whereClause }
            .count()

        val productQuery = ProductQuery
            .scopedSelectQuery
            .where { whereClause }
            .limit(limit, offset)
            .toList()

        val productAttributesMap = fetchProductAttributesMap(
            productQuery.map { it[ProductTable.id].value }
        )

        val count = min(productsCount, productCountByOptions ?: 0)
        val pageCount = ceil(count.toDouble() / pageSize.toDouble()).toLong()

        return@newSuspendedTransaction ProductFetchResultModel(
            products = productQuery.map {
                val id = it[ProductTable.id].value
                val list = productAttributesMap[id] ?: listOf()
                ProductQuery.map(it, list)
            },
            meta = PaginationMetaModel(
                page = page.toLong(),
                pageSize = pageSize.toLong(),
                pageCount = pageCount,
            )
        )
    }

    private fun fetchProductAttributesMap(
        productsList: List<Int>
    ): Map<Int, List<ProductAttributeModel>> = transaction {
        val optionsRows = ProductAttributeOptionsQuery
            .baseQuery
            .where { ProductAttributeValuesTable.productId inList productsList }
            .toList()

        val attributesMap = mutableMapOf<Int, List<ProductAttributeModel>>()

        val rowsPerProduct = mutableMapOf<Int, MutableList<ResultRow>>()
        for (row in optionsRows) {
            val productId = row[ProductAttributeValuesTable.productId]
            rowsPerProduct.getOrPut(productId) { mutableListOf() }.add(row)
        }

        for ((productId, rows) in rowsPerProduct) {
            attributesMap[productId] = ProductAttributeOptionsQuery.mapProductAttributes(rows)
        }

        return@transaction attributesMap
    }

    // returns products count, and filter options
    private fun productsOptionsFilter(
        list: List<String>,
        limit: Int,
        offset: Long,
    ): Pair<Int, Op<Boolean>>? = transaction {
        if (list.isEmpty()) { return@transaction null }

        val attributes = mutableSetOf<Int>()
        val options = mutableSetOf<Int>()

        for (pair in list.map { parseAttributeOptionPair(it) }) {
            attributes.add(pair.first)
            options.add(pair.second)
        }

        val whereClause = (ProductAttributeValuesTable.attributeId inList attributes.toList())
            .and(ProductAttributeValuesTable.optionId inList options.toList())

        val count = ProductAttributeValuesTable
            .selectAll()
            .where { whereClause }
            .count()
            .toInt()

        val idList: List<Int> = ProductAttributeValuesTable
            .select(ProductAttributeValuesTable.productId)
            .where { whereClause }
            .limit(limit, offset)
            .toList()
            .map { it[ProductAttributeValuesTable.productId] }

        return@transaction Pair(count, ProductTable.id inList idList)
    }

    private fun buildProductCriteriaConditions(
        criteria: ProductFetchCriteriaModel
    ): List<Op<Boolean>> {
        val conditions = mutableListOf<Op<Boolean>>()

        criteria.categoryId?.let {
            conditions += ProductTable.categoryId eq it.toInt()
        }

        criteria.query?.let {
            conditions += ProductTable.title like it
        }

        criteria.minPriceCents?.let {
            conditions += ProductTable.priceCents greaterEq it
        }

        criteria.maxPriceCents?.let {
            conditions += ProductTable.priceCents lessEq it
        }

        return conditions
    }

    override suspend fun batchCreateProduct(
        input: List<ProductCreateInputModel>
    ) = transaction {
        for (product in input) {
            val id = ProductTable.insertAndGetId {
                it[title] = product.title
                it[categoryId] = product.categoryId
                it[priceCents] = product.priceCents
            }.value

            ProductAttributeValuesTable.batchInsert(product.attributeOptions) { string ->
                val (attribute, option) = parseAttributeOptionPair(string)
                this[ProductAttributeValuesTable.productId] = id
                this[ProductAttributeValuesTable.attributeId] = attribute
                this[ProductAttributeValuesTable.optionId] = option
            }
        }
    }

    private fun parseAttributeOptionPair(string: String): Pair<Int, Int> {
        val list = string.split(":", limit = 2)

        if (list.count() != 2) {
            throw IOException("Incorrect attribute:option pattern")
        }

        val attribute = list[0].toInt()
        val option = list[1].toInt()

        return Pair(attribute, option)
    }

    override suspend fun batchCreateProductAttribute(
        input: List<ProductAttributeCreateInputModel>
    ): Unit = transaction {
        ProductAttributesTable.batchInsert(input) { value ->
            this[ProductAttributesTable.code] = value.code
            this[ProductAttributesTable.categoryId] = value.categoryId
        }
    }

    override suspend fun batchCreateProductAttributeOption(
        input: List<ProductAttributeOptionCreateInputModel>
    ): Unit = transaction {
        ProductAttributeOptionsTable.batchInsert(input) { value ->
            this[ProductAttributeOptionsTable.attributeId] = value.attributeId.toInt()
            this[ProductAttributeOptionsTable.code] = value.code
        }
    }

    override suspend fun batchCreateProductCategory(
        input: List<ProductCategoryCreateInputModel>
    ): Unit = transaction {
        ProductCategoriesTable.batchInsert(input) { value ->
            this[ProductCategoriesTable.code] = value.code
        }
    }

    private fun toLimitOffset(page: Int, pageSize: Int): Pair<Int, Long> {
        return Pair(pageSize, ((page - 1) * pageSize).toLong())
    }
}
