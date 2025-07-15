package io.kuz.ecom.product.domain.model

data class ProductCreateInputModel(
    val title: String,
    val categoryId: Long,
    val attributeOptions: List<String>,
    val priceCents: Long,
)
