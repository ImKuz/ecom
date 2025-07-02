package io.kuz.ecom.common.models.common

data class PaginatedListModel<T>(
    val list: List<T>,
    val meta: PaginationMetaModel,
)