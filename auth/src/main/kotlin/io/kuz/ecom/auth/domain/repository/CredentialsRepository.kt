package io.kuz.ecom.auth.domain.repository

import io.kuz.ecom.auth.domain.model.AuthProvider
import io.kuz.ecom.auth.domain.model.CredentialsRecordModel

interface CredentialsRepository {

    fun createRecord(record: CredentialsRecordModel)
    fun readRecord(provider: AuthProvider, identifier: String): CredentialsRecordModel?
}