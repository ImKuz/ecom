package io.kuz.ecom.product.domain.model

data class ProductFetchCriteriaModel(
    val query: String?,
    val categoryId: Long?,
    val attributeOptions: List<String>,
    val minPriceCents: Long?,
    val maxPriceCents: Long?,
)