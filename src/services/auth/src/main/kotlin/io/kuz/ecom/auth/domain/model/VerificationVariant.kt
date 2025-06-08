package io.kuz.ecom.auth.domain.model

sealed class VerificationVariant {
    data class Email(val value: String): VerificationVariant()
}