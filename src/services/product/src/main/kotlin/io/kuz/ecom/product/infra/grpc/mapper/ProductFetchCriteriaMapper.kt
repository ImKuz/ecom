package io.kuz.ecom.product.infra.grpc.mapper

import io.kuz.ecom.product.domain.model.ProductFetchCriteriaModel
import io.kuz.ecom.proto.product.ProductFetchCriteria

object ProductFetchCriteriaMapper {

    fun toModel(grpc: ProductFetchCriteria): ProductFetchCriteriaModel {
        return ProductFetchCriteriaModel(
            query = grpc.takeIf { it.hasQuery() }?.query,
            categoryId = grpc.takeIf { it.hasCategoryId() }?.categoryId,
            attributeOptions = grpc.attributeOptionsList,
            minPriceCents = grpc.takeIf { it.hasMinPriceCents() }?.minPriceCents,
            maxPriceCents = grpc.takeIf { it.hasMaxPriceCents() }?.maxPriceCents,
        )
    }
}