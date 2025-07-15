package io.kuz.ecom.product.domain.model

data class ProductAttributeCreateInputModel(
    val categoryId: Long,
    val code: String,
)