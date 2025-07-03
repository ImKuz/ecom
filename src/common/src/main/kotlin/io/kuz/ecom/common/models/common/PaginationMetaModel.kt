package io.kuz.ecom.common.models.common

data class PaginationMetaModel(
    val page: Long,
    val pageSize: Long,
    val pageCount: Long,
)