package io.kuz.ecom.common.models

data class ProductModel(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val categoryCode: String,
    val attributes: List<ProductAttributeModel>,
    val priceCents: Long,
)

data class ProductAttributeModel(
    val productId: Int,
    val id: Int,
    val code: String,
    val options: List<ProductAttributeOptionModel>,
)

data class ProductAttributeOptionModel(
    val id: Int,
    val attributeId: Int,
    val code: String,
)