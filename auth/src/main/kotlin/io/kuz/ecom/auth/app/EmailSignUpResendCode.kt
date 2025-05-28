package io.kuz.ecom.auth.app

import io.kuz.ecom.auth.domain.AuthService

class EmailSignUpResendCode(
    private val service: AuthService,
) {
    suspend operator fun invoke(sessionId: String) {
        service.resendVerificationCode(sessionId)
    }
}