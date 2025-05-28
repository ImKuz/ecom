package io.kuz.ecom.auth.domain.model

data class SessionTokensData(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)
