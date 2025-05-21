package io.kuz.ecom.common.dto

import java.math.BigDecimal

data class ProductDTO(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val categoryLabel: String,
    val attributes: List<ProductAttributeDTO>,
    val price: BigDecimal,
)

data class ProductAttributeDTO(
    val id: Int,
    val label: String,
    val options: List<ProductAttributeOptionDTO>,
)

data class ProductAttributeOptionDTO(
    val id: Int,
    val attributeId: Int,
    val label: String,
)