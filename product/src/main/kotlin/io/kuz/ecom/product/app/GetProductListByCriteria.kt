package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.ProductFetchCriteriaModel
import io.kuz.ecom.product.domain.model.ProductFetchResultModel
import org.springframework.stereotype.Component

@Component
class GetProductListByCriteria(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(
        criteria: ProductFetchCriteriaModel,
        limit: Long,
        offset: Long,
    ): ProductFetchResultModel {
        try {
            return repo.getProductList(
                criteria = criteria,
                limit = limit,
                offset = offset,
            )
        } catch (e: Exception) {
            println("Exception in GetProductListByCriteria: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
}