package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.ProductCreateInputModel
import org.springframework.stereotype.Component

@Component
class BatchCreateProduct(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(input: List<ProductCreateInputModel>) {
        repo.batchCreateProduct(input)
    }
}