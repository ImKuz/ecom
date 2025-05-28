package io.kuz.ecom.auth.infra

import io.kuz.ecom.auth.domain.VerificationRepository
import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import java.time.Instant

class VerificationRepositoryImpl: VerificationRepository {

    override fun createVerificationSession(
        data: VerificationSessionData,
        variant: VerificationVariant
    ) {

    }

    override fun readVerificationSession(variant: VerificationVariant): VerificationSessionData? {
        TODO()
    }

    override fun readVerificationSession(id: String): VerificationSessionData? {
        TODO()
    }

    override fun readVerificationSessionVariant(id: String): VerificationVariant? {
        TODO()
    }

    override fun updateVerificationSession(
        id: String,
        expectedCode: String,
        retryTimeout: Instant
    ) {

    }

    override fun updateVerificationSession(
        id: String,
        isConfirmed: Boolean
    ) {

    }

    override fun deleteVerificationSession(id: String) {

    }
}