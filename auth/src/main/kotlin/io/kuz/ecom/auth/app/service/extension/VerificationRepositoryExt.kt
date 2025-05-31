package io.kuz.ecom.auth.app.service.extension

import io.kuz.ecom.auth.domain.exception.ExpiredException
import io.kuz.ecom.auth.domain.exception.NotFoundException
import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.repository.VerificationRepository
import java.time.Instant

suspend fun VerificationRepository.readChecked(sessionId: String): VerificationSessionData {
    val data = readVerificationSession(sessionId) ?: throw NotFoundException()
    if (data.ttl <= Instant.now()) throw ExpiredException()
    return data
}