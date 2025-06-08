package io.kuz.ecom.auth.domain

interface PasswordHasher {
    suspend fun hash(password: String): String
    suspend fun verify(password: String, hash: String): Boolean
}