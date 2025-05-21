package io.kuz.ecom.product.domain.model

data class ProductCreateInputModel(
    val title: String,
    val categoryId: Int,
    val attributeOptions: List<String>,
    val priceCents: Long,
)
