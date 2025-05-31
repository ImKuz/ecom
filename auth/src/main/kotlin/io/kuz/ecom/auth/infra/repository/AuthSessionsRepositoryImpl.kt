package io.kuz.ecom.auth.infra.repository

import io.kuz.ecom.auth.domain.repository.AuthSessionsRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Service

@Service
class AuthSessionsRepositoryImpl(
    private val template: ReactiveStringRedisTemplate,
): AuthSessionsRepository {

    override suspend fun create(userId: String, sessionId: String) {
        template
            .opsForSet()
            .add(key(userId), sessionId)
            .awaitSingle()
    }

    override suspend fun delete(userId: String, sessionId: String) {
        template
            .opsForSet()
            .remove(key(userId), sessionId)
            .awaitSingle()
    }

    private fun key(userId: String): String = "sesh:${userId}"
}