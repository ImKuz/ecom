package io.kuz.ecom.auth.domain

import io.kuz.ecom.auth.domain.model.TokenMetadata

interface TokenService {

    fun createAccessToken(
        userId: String,
        sessionId: String,
    ): String

    fun createRefreshToken(
        userId: String,
        sessionId: String,
    ): String

    fun parseMetadata(
        tokenString: String
    ): TokenMetadata
}