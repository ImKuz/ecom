package io.kuz.ecom.auth.app.service

import io.kuz.ecom.auth.app.service.extension.readChecked
import io.kuz.ecom.auth.domain.VerificationCodeService
import io.kuz.ecom.auth.domain.SessionVerificationService
import io.kuz.ecom.auth.domain.exception.AlreadyExistsException
import io.kuz.ecom.auth.domain.exception.InvalidCredentialsException
import io.kuz.ecom.auth.domain.exception.TooEarlyException
import io.kuz.ecom.auth.domain.model.AuthProvider
import io.kuz.ecom.auth.domain.model.VerificationSessionData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import io.kuz.ecom.auth.domain.repository.CredentialsRepository
import io.kuz.ecom.auth.domain.repository.VerificationRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class SessionVerificationServiceImpl(
    private val credentialsRepo: CredentialsRepository,
    private val verificationRepo: VerificationRepository,
    private val codeService: VerificationCodeService,
    @Value("\${app.verify.ttl}")
    private val verifySessionTTL: Long,
    @Value("\${app.verify.retry-timeout}")
    private val verifySessionRetryTimeout: Long,
):SessionVerificationService {

    override suspend fun initiateVerification(variant: VerificationVariant): VerificationSessionData {
        val provider: AuthProvider
        val identifier: String

        when(variant) {
            is VerificationVariant.Email -> {
                provider = AuthProvider.LOCAL
                identifier = variant.value
            }
        }

        if (credentialsRepo.readRecord(provider, identifier) == null) {
            throw AlreadyExistsException()
        }

        verificationRepo.readVerificationSession(variant)?.let {
            verificationRepo.deleteVerificationSession(it.id)
        }

        val data = VerificationSessionData(
            id = UUID.randomUUID().toString(),
            expectedCode = codeService.createCode(),
            ttl = Instant.now().plusSeconds(verifySessionTTL),
            retryTimeout = Instant.now().plusSeconds(verifySessionRetryTimeout),
            isConfirmed = false,
        )

        verificationRepo.createVerificationSession(data, variant)
        return data
    }

    override suspend fun resendVerificationCode(sessionId: String) {
        val data = verificationRepo.readChecked(sessionId)
        val now = Instant.now()

        if (data.retryTimeout > now) {
            throw TooEarlyException()
        }

        verificationRepo.updateVerificationSession(
            sessionId,
            expectedCode = codeService.createCode(),
            retryTimeout = now.plusSeconds(verifySessionRetryTimeout),
        )
    }

    override suspend fun confirmVerificationData(sessionId: String, code: String) {
        val data = verificationRepo.readChecked(sessionId)

        if (data.expectedCode == code) {
            verificationRepo.updateVerificationSession(sessionId, isConfirmed = true)
        } else {
            throw InvalidCredentialsException()
        }
    }
}