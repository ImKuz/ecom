package io.kuz.ecom.auth.app

import io.kuz.ecom.auth.domain.VerificationRepository
import io.kuz.ecom.auth.domain.AuthService
import io.kuz.ecom.auth.domain.model.VerificationVariant

class EmailSignUpInitiateVerification(
    private val service: AuthService,
) {
    suspend operator fun invoke(variant: VerificationVariant) {
        service.initiateVerification(variant)
    }
}