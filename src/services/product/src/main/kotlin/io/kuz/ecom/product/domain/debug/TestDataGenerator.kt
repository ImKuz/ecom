package io.kuz.ecom.product.domain.debug

interface TestDataGenerator {

    suspend fun generate(
        categoriesCount: Long,
        attributesCount: Long,
        optionsCount: Long,
        productsCount: Long,
    )
}