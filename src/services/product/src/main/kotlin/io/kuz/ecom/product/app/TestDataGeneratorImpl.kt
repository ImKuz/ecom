package io.kuz.ecom.product.app

import io.kuz.ecom.product.domain.debug.TestDataGenerator
import io.kuz.ecom.product.domain.model.ProductAttributeCreateInputModel
import io.kuz.ecom.product.domain.model.ProductAttributeOptionCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCategoryCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCreateInputModel
import io.kuz.ecom.product.infra.db.table.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class TestDataGeneratorImpl(
    private val batchCreateProductCase: BatchCreateProduct,
    private val batchCreateProductAttributeCase: BatchCreateProductAttribute,
    private val batchCreateProductAttributeOptionCase: BatchCreateProductAttributeOption,
    private val batchCreateProductCategoryCase: BatchCreateProductCategory,
): TestDataGenerator {

    override suspend fun generate(
        categoriesCount: Long,
        attributesCount: Long,
        optionsCount: Long,
        productsCount: Long
    ) {
        clearData()

        generateCategories(categoriesCount)

        for (categoryId in 1..categoriesCount) {
            generateAttributes(categoryId, attributesCount)
            generateOptions(categoryId, attributesCount, optionsCount)
        }

        generateProducts(
            productsCount,
            categoriesCount,
            attributesCount,
            optionsCount,
        )
    }

    private suspend fun generateProducts(
        productsCount: Long,
        categoriesCount: Long,
        attributesCount: Long,
        optionsCount: Long,
    ) = runBlocking {
        val inputs = (1..productsCount).map { productId ->
            val categoryId = (1..categoriesCount).random()
            val attributeOptions = mutableListOf<String>()

            for (attributeNumber in 1..attributesCount) {
                val attributeId = attributeId(
                    categoryId = categoryId,
                    attributeNumber = attributeNumber,
                    attributesCount = attributesCount,
                )

                for (optionNumber in 1..optionsCount) {
                    val optionId = optionId(
                        categoryId = categoryId,
                        attributeNumber = attributeNumber,
                        attributesCount = attributesCount,
                        optionNumber = optionNumber,
                        optionsCount = optionsCount,
                    )

                    if (optionId == 1L || Random.nextBoolean()) {
                        attributeOptions.add("$attributeId:$optionId")
                    }
                }
            }

            return@map ProductCreateInputModel(
                title = "Product $productId",
                categoryId = categoryId,
                attributeOptions = attributeOptions,
                priceCents = (100L..1000000L).random(),
            )
        }

        batchCreateProductCase(inputs)
    }

    private suspend fun generateAttributes(
        categoryId: Long,
        attributesCount: Long,
    ) = runBlocking {
        batchCreateProductAttributeCase(
            (1..attributesCount).map { attributeNumber ->
                ProductAttributeCreateInputModel(
                    categoryId = categoryId,
                    code = "cat$categoryId.att$attributeNumber"
                )
            }
        )
    }

    private suspend fun generateOptions(
        categoryId: Long,
        attributesCount: Long,
        optionsCount: Long,
    ) = runBlocking {
        val inputs = mutableListOf<ProductAttributeOptionCreateInputModel>()
        (1..attributesCount).forEach { attributeNumber ->
            val attributeId = attributeId(
                categoryId = categoryId,
                attributeNumber = attributeNumber,
                attributesCount = attributesCount
            )

            inputs.addAll(
                (1..optionsCount).map { optionNumber ->
                    ProductAttributeOptionCreateInputModel(
                        attributeId = attributeId,
                        code = "cat$categoryId.att$attributeNumber.opt$optionNumber"
                    )
                }
            )
        }
        batchCreateProductAttributeOptionCase(inputs)
    }


    private suspend fun generateCategories(count: Long) = runBlocking {
        val inputs = mutableListOf<ProductCategoryCreateInputModel>()
        for (id in 1..count) {
            inputs.add(
                ProductCategoryCreateInputModel(
                    code = "cat$id"
                )
            )
        }
        batchCreateProductCategoryCase(inputs)
    }

    private fun attributeId(
        categoryId: Long,
        attributeNumber: Long,
        attributesCount: Long
    ) = attributeNumber + ((categoryId - 1) * attributesCount)

    private fun optionId(
        categoryId: Long,
        attributeNumber: Long,
        attributesCount: Long,
        optionNumber: Long,
        optionsCount: Long,
    ): Long {
        val optionsPerCategory = attributesCount * optionsCount
        val optionsInAttributes = (attributeNumber - 1) * optionsCount
        return ((categoryId - 1) * optionsPerCategory) + optionsInAttributes + optionNumber
    }

    private fun clearData() = transaction {
        SchemaUtils.drop(
            ProductAttributeValuesTable,
            ProductAttributeOptionsTable,
            ProductAttributesTable,
            ProductTable,
            ProductCategoriesTable,
        )

        SchemaUtils.create(
            ProductCategoriesTable,
            ProductTable,
            ProductAttributesTable,
            ProductAttributeOptionsTable,
            ProductAttributeValuesTable,
        )
    }
}