package io.kuz.ecom.product.infra.db.query

import io.kuz.ecom.common.models.ProductAttributeModel
import io.kuz.ecom.common.models.ProductAttributeOptionModel
import io.kuz.ecom.product.infra.db.table.ProductAttributeOptionsTable
import io.kuz.ecom.product.infra.db.table.ProductAttributeValuesTable
import io.kuz.ecom.product.infra.db.table.ProductAttributesTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

object ProductAttributeOptionsQuery {

    val baseQuery: Query get() = ProductAttributeValuesTable
        .join(
            ProductAttributesTable,
            JoinType.INNER,
            onColumn = ProductAttributeValuesTable.attributeId,
            otherColumn = ProductAttributesTable.id,
        )
        .join(
            ProductAttributeOptionsTable,
            JoinType.INNER,
            onColumn = ProductAttributeValuesTable.optionId,
            otherColumn = ProductAttributeOptionsTable.id,
        )
        .select(
            ProductAttributeValuesTable.productId,
            ProductAttributesTable.id,
            ProductAttributesTable.code,
            ProductAttributeOptionsTable.id,
            ProductAttributeOptionsTable.code,
        )

    fun mapProductAttributes(rows: List<ResultRow>): List<ProductAttributeModel> {
        val optionsPerAttribute = mutableMapOf<Int, MutableList<ProductAttributeOptionModel>>()
        val attributes = mutableListOf<ProductAttributeModel>()

        for (row in rows) {
            val attributeId = row[ProductAttributesTable.id]

            val option = ProductAttributeOptionModel(
                id = row[ProductAttributeOptionsTable.id],
                attributeId = attributeId,
                code = row[ProductAttributeOptionsTable.code],
            )

            optionsPerAttribute.getOrPut(attributeId) { mutableListOf() }.add(option)
        }

        for (row in rows.distinctBy { it[ProductAttributesTable.id] }) {
            val attributeId = row[ProductAttributesTable.id]
            val options = optionsPerAttribute[attributeId] ?: continue
            attributes.add(
                ProductAttributeModel(
                    productId = row[ProductAttributeValuesTable.productId],
                    id = attributeId,
                    code = row[ProductAttributesTable.code],
                    options = options
                )
            )
        }

        return attributes
    }
}