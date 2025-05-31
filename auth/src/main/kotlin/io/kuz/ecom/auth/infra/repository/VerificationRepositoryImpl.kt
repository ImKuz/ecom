package io.kuz.ecom.auth.infra.repository

import com.fasterxml.jackson.databind.ObjectMapper
import io.kuz.ecom.auth.domain.repository.VerificationRepository
import io.kuz.ecom.auth.domain.exception.ExpiredException
import io.kuz.ecom.auth.domain.exception.NotFoundException
import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.getAndAwait
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class VerificationRepositoryImpl(
    private val template: ReactiveStringRedisTemplate,
    private val objectMapper: ObjectMapper,
    @Value("\${app.verify.ttl}")
    private val verifySessionTTL: Long,
): VerificationRepository {

    private val ops: ReactiveValueOperations<String, String>
        get() = template.opsForValue()

    override suspend fun createVerificationSession(
        data: VerificationSessionData,
        variant: VerificationVariant
    ) = coroutineScope {
        val json = objectMapper.writeValueAsString(data)
        val variantKey = variantToKey(variant)
        val duration = Duration.of(verifySessionTTL, ChronoUnit.SECONDS)

        awaitAll(
            async { ops.set(seshToVarKey(data.id), variantKey, duration).awaitSingle() },
            async { ops.set(varToSeshKey(variant), data.id, duration).awaitSingle() },
            async { ops.set(seshDataKey(data.id), json, duration).awaitSingle() },
        )

        return@coroutineScope
    }

    override suspend fun readVerificationSession(variant: VerificationVariant): VerificationSessionData? {
        ops.getAndAwait(varToSeshKey(variant))?.let { id ->
            return readVerificationSession(id)
        } ?: return null
    }

    override suspend fun readVerificationSession(id: String): VerificationSessionData? {
        ops.getAndAwait(seshDataKey(id))?.let { json ->
            return objectMapper.readValue(json, VerificationSessionData::class.java)
        } ?: return null
    }

    override suspend fun readVerificationSessionVariant(id: String): VerificationVariant? {
        ops.getAndAwait(seshToVarKey(id))?.let { string ->
            return keyToVariant(string)
        } ?: return null
    }

    override suspend fun updateVerificationSession(
        id: String,
        expectedCode: String,
        retryTimeout: Instant
    ) {
        updateSession(id) {
            VerificationSessionData(
                id = it.id,
                expectedCode = expectedCode,
                ttl = it.ttl,
                retryTimeout = retryTimeout,
                isConfirmed = false
            )
        }
    }

    override suspend fun updateVerificationSession(
        id: String,
        isConfirmed: Boolean
    ) {
        updateSession(id) {
            VerificationSessionData(
                id = it.id,
                expectedCode = it.expectedCode,
                ttl = it.ttl,
                retryTimeout = it.retryTimeout,
                isConfirmed = isConfirmed
            )
        }
    }

    override suspend fun deleteVerificationSession(id: String) {
        coroutineScope {
            readVerificationSessionVariant(id)?.let {
                ops.delete(varToSeshKey(it)).awaitSingle()
            }
            awaitAll(
                async { ops.delete(seshToVarKey(id)) },
                async { ops.delete(seshDataKey(id)) }
            )
        }
    }

    private suspend fun updateSession(
        id: String,
        updater: (VerificationSessionData) -> VerificationSessionData
    ) {
        readVerificationSession(id)?.let {
            if (it.ttl <= Instant.now() || it.isConfirmed) {
                throw ExpiredException()
            }
            val updatedSession = updater(it)
            val json = objectMapper.writeValueAsString(updatedSession)
            ops.set(seshDataKey(id), json).awaitSingle()
        } ?: throw NotFoundException()
    }

    private fun seshToVarKey(id: String) = "verif:sesh:${id}"
    private fun varToSeshKey(variant: VerificationVariant) = "verif:variant:${variantToKey(variant)}"
    private fun seshDataKey(id: String) = "verif:sesh-data:${id}"

    private fun keyToVariant(key: String): VerificationVariant {
        val items = key.splitToSequence(":", limit = 2)
        val exception = IllegalArgumentException("Unexpected verification variant string")

        if (items.count() != 2) { throw exception }

        when (items.first()) {
            "email" -> return VerificationVariant.Email(items.last())
            else -> throw exception
        }
    }

    private fun variantToKey(variant: VerificationVariant): String {
        when (variant) {
            is VerificationVariant.Email -> return "email:${variant.value}"
        }
    }
}