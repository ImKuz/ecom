package io.kuz.ecom.auth.domain.repository

import io.kuz.ecom.auth.domain.model.AuthProvider
import io.kuz.ecom.auth.domain.model.CredentialsRecordModel

interface CredentialsRepository {

    suspend fun createRecord(record: CredentialsRecordModel)
    suspend fun readRecord(provider: AuthProvider, identifier: String): CredentialsRecordModel?
}