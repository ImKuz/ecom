package io.kuz.ecom.common.models.common

data class PaginationMetaModel(
    val limit: Long,
    val offset: Long,
    val total: Long,
)