package io.kuz.ecom.product.infra.db.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProductTable: IntIdTable("products") {

    val categoryId = integer("category_id")
        .references(
            ProductCategoriesTable.id,
            onDelete = ReferenceOption.CASCADE
        )

    val title = varchar("title", 128)

    val priceCents = long("price_cents")
}