package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.ProductRepository
import io.kuz.ecom.product.domain.model.ProductAttributeOptionCreateInputModel
import org.springframework.stereotype.Component

@Component
class BatchCreateProductAttributeOption(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(input: List<ProductAttributeOptionCreateInputModel>) {
        repo.batchCreateProductAttributeOption(input)
    }
}