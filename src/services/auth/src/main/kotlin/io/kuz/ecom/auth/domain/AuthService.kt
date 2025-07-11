package io.kuz.ecom.auth.domain

import io.kuz.ecom.auth.domain.model.*

interface AuthService {
    suspend fun signUp(sessionId: String, password: String): SessionTokensData
    suspend fun login(email: String, password: String): SessionTokensData
    suspend fun logout(accessToken: String)
    suspend fun verifyToken(token: String)
}