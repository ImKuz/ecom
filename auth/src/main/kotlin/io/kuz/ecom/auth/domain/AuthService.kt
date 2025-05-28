package io.kuz.ecom.auth.domain

import io.kuz.ecom.auth.domain.model.*

interface AuthService {

    suspend fun initiateVerification(variant: VerificationVariant): VerificationSessionData
    suspend fun resendVerificationCode(sessionId: String)
    suspend fun confirmVerificationData(sessionId: String, code: String): Boolean
    suspend fun signUp(sessionId: String, password: String)
    suspend fun login(email: String, password: String): SessionTokensData
    suspend fun logout(accessToken: String)
}