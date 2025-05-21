package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.ProductCategoryCreateInputModel
import org.springframework.stereotype.Component

@Component
class BatchCreateProductCategory(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(input: List<ProductCategoryCreateInputModel>) {
        repo.batchCreateProductCategory(input)
    }
}