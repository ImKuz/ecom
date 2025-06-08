package io.kuz.ecom.product.infra.db.query

import io.kuz.ecom.common.models.product.ProductAttributeModel
import io.kuz.ecom.common.models.product.ProductModel
import io.kuz.ecom.product.infra.db.table.ProductCategoriesTable
import io.kuz.ecom.product.infra.db.table.ProductTable
import org.jetbrains.exposed.sql.Join
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

object ProductQuery {

    val baseQuery: Join get() = ProductTable
        .join(
            ProductCategoriesTable,
            JoinType.INNER,
            onColumn = ProductTable.categoryId,
            otherColumn = ProductCategoriesTable.id
        )

    val scopedSelectQuery: Query get() = baseQuery
        .select(
            ProductTable.id,
            ProductTable.categoryId,
            ProductCategoriesTable.code,
            ProductTable.title,
            ProductTable.priceCents,
        )

    fun map(
        row: ResultRow,
        attributes: List<ProductAttributeModel>
    ): ProductModel {
        return ProductModel(
            id = row[ProductTable.id].value,
            title = row[ProductTable.title],
            categoryId = row[ProductTable.categoryId],
            categoryCode = row[ProductCategoriesTable.code],
            priceCents = row[ProductTable.priceCents],
            attributes = attributes
        )
    }
}