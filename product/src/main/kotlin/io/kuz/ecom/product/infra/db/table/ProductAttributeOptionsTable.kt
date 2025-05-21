package io.kuz.ecom.product.infra.db.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProductAttributeOptionsTable: Table("product_attribute_options") {

    val id = integer("id")
        .autoIncrement()

    val code = varchar("code", 128)

    val attributeId = integer("attribute_id")
        .references(
            ProductAttributesTable.id,
            onDelete = ReferenceOption.CASCADE
        )

    override val primaryKey = PrimaryKey(id)
}