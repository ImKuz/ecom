package io.kuz.ecom.auth.domain

interface AuthSessionsRepository {

    fun create(userId: String, sessionId: String)
    fun delete(userId: String, sessionId: String)
}