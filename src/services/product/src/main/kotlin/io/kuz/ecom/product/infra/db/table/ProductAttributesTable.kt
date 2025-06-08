package io.kuz.ecom.product.infra.db.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProductAttributesTable: Table("product_attributes") {

    val id = integer("id")
        .autoIncrement()

    val code = varchar("code", 128)

    val categoryId = integer("category_id")
        .references(
            ProductCategoriesTable.id,
            onDelete = ReferenceOption.CASCADE
        )

    override val primaryKey = PrimaryKey(id)
}