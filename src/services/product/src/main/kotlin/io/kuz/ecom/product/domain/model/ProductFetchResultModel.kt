package io.kuz.ecom.product.domain.model

import io.kuz.ecom.common.models.common.PaginationMetaModel
import io.kuz.ecom.common.models.product.ProductModel

data class ProductFetchResultModel(
    val products: List<ProductModel>,
    val meta: PaginationMetaModel,
)