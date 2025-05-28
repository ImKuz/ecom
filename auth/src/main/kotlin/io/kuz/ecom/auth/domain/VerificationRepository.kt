package io.kuz.ecom.auth.domain

import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import java.time.Instant

interface VerificationRepository {

    fun createVerificationSession(
        data: VerificationSessionData,
        variant: VerificationVariant
    )

    fun readVerificationSession(variant: VerificationVariant): VerificationSessionData?

    fun readVerificationSession(id: String): VerificationSessionData?

    fun readVerificationSessionVariant(id: String): VerificationVariant?

    fun updateVerificationSession(
        id: String,
        expectedCode: String,
        retryTimeout: Instant
    )

    fun updateVerificationSession(
        id: String,
        isConfirmed: Boolean
    )

    fun deleteVerificationSession(id: String)
}