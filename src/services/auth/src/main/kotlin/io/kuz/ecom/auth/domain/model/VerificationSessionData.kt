package io.kuz.ecom.auth.domain.model

import java.time.Instant

data class VerificationSessionData(
    val id: String,
    val expectedCode: String,
    val ttl: Instant,
    val retryTimeout: Instant,
    val isConfirmed: Boolean,
)