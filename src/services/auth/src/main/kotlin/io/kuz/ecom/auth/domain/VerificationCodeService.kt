package io.kuz.ecom.auth.domain

interface VerificationCodeService {
    fun createCode(): String
}