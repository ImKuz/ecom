package io.kuz.ecom.auth.domain.repository

interface AuthSessionsRepository {

    suspend fun create(userId: String, sessionId: String)
    suspend fun readSessions(userId: String): Set<String>
    suspend fun delete(userId: String, sessionId: String)
}