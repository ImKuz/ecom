package io.kuz.ecom.product.domain

import io.kuz.ecom.common.models.product.ProductModel
import io.kuz.ecom.product.domain.model.*

interface ProductRepository {

    suspend fun getProductById(id: Int): ProductModel?

    suspend fun batchCreateProduct(input: List<ProductCreateInputModel>)

    suspend fun createProduct(input: ProductCreateInputModel) {
        batchCreateProduct(listOf(input))
    }

    suspend fun getProductList(
        criteria: ProductFetchCriteriaModel,
        page: Int,
        pageSize: Int,
    ): ProductFetchResultModel

    suspend fun batchCreateProductAttribute(input: List<ProductAttributeCreateInputModel>)

    suspend fun createProductAttribute(input: ProductAttributeCreateInputModel) {
        batchCreateProductAttribute(listOf(input))
    }

    suspend fun batchCreateProductAttributeOption(input: List<ProductAttributeOptionCreateInputModel>)

    suspend fun createProductAttributeOption(input: ProductAttributeOptionCreateInputModel) {
        batchCreateProductAttributeOption(listOf(input))
    }

    suspend fun batchCreateProductCategory(input: List<ProductCategoryCreateInputModel>)

    suspend fun createProductCategory(input: ProductCategoryCreateInputModel) {
        batchCreateProductCategory(listOf(input))
    }
}