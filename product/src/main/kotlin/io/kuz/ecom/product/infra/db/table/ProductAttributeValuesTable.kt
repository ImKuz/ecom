package io.kuz.ecom.product.infra.db.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProductAttributeValuesTable: Table("product_attribute_values") {

    val attributeId = integer("attribute_id")
        .references(
            ProductAttributesTable.id,
            onDelete = ReferenceOption.CASCADE
        )

    val productId =  integer("product_id")
        .references(
            ProductTable.id,
            onDelete = ReferenceOption.CASCADE
        )

    val optionId =  integer("option_id")
        .references(
            ProductAttributeOptionsTable.id,
            onDelete = ReferenceOption.CASCADE
        )
}