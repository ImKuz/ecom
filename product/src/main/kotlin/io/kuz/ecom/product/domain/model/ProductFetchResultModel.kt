package io.kuz.ecom.product.domain.model

import io.kuz.ecom.common.models.PaginationMetaModel
import io.kuz.ecom.common.models.ProductModel

data class ProductFetchResultModel(
    val products: List<ProductModel>,
    val meta: PaginationMetaModel,
)