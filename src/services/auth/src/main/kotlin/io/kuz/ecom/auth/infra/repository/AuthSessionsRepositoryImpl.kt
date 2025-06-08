package io.kuz.ecom.auth.infra.repository

import io.kuz.ecom.auth.domain.repository.AuthSessionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AuthSessionsRepositoryImpl(
    private val template: ReactiveStringRedisTemplate,
): AuthSessionsRepository {

    override suspend fun create(userId: String, sessionId: String) {
        withContext(Dispatchers.IO) {
            template
                .opsForSet()
                .add(key(userId), sessionId)
                .awaitSingle()
        }
    }

    override suspend fun readSessions(
        userId: String
    ): Set<String> = withContext(Dispatchers.IO) {
        template
            .opsForSet()
            .members(key(userId))
            .collect(Collectors.toSet())
            .defaultIfEmpty(emptySet())
            .awaitSingle()
    }

    override suspend fun delete(userId: String, sessionId: String) {
        withContext(Dispatchers.IO) {
            template
                .opsForSet()
                .remove(key(userId), sessionId)
                .awaitSingle()
        }
    }

    private fun key(userId: String): String = "sesh:${userId}"
}