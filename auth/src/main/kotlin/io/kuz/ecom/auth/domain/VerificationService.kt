package io.kuz.ecom.auth.domain

import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant

interface VerificationService {
    suspend fun initiateVerification(variant: VerificationVariant): VerificationSessionData
    suspend fun resendVerificationCode(sessionId: String)
    suspend fun confirmVerificationData(sessionId: String, code: String): Boolean
}