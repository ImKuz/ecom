package io.kuz.ecom.product.infra.db.table

import org.jetbrains.exposed.dao.id.IntIdTable

object ProductCategoriesTable: IntIdTable("product_categories") {

    val code = varchar("code", 128)
}