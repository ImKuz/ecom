package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.ProductAttributeCreateInputModel
import org.springframework.stereotype.Component

@Component
class BatchCreateProductAttribute(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(input: List<ProductAttributeCreateInputModel>) {
        repo.batchCreateProductAttribute(input)
    }
}