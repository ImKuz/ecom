package io.kuz.ecom.auth.domain.model

import java.time.Instant

data class TokenMetadata(
    val userId: String,
    val sessionId: String,
    val createdAt: Instant,
    val expiresAt: Instant,
)
