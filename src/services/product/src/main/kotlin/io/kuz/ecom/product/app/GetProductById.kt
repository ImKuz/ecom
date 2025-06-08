package io.kuz.ecom.product.app

import io.kuz.ecom.common.models.product.ProductModel
import io.kuz.ecom.product.domain.ProductRepository
import org.springframework.stereotype.Component

@Component
class GetProductById(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(id: Int): ProductModel? {
        return repo.getProductById(id)
    }
}