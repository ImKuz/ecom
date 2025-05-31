package io.kuz.ecom.auth.domain.repository

import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import java.time.Instant

interface VerificationRepository {

    suspend fun createVerificationSession(
        data: VerificationSessionData,
        variant: VerificationVariant
    )

    suspend fun readVerificationSession(variant: VerificationVariant): VerificationSessionData?

    suspend fun readVerificationSession(id: String): VerificationSessionData?

    suspend fun readVerificationSessionVariant(id: String): VerificationVariant?

    suspend fun updateVerificationSession(
        id: String,
        expectedCode: String,
        retryTimeout: Instant
    )

    suspend fun updateVerificationSession(
        id: String,
        isConfirmed: Boolean
    )

    suspend fun deleteVerificationSession(id: String)
}