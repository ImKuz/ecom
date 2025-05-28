package io.kuz.ecom.auth.domain.model

import java.util.UUID

sealed class Credentials {
    data class Local(
        val email: String,
        val passwordHash: String,
    ): Credentials()
}

data class CredentialsRecordModel(
    val userId: UUID,
    val credentials: Credentials,
)
