package io.kuz.ecom.auth.app.service

import io.kuz.ecom.auth.domain.*
import io.kuz.ecom.auth.domain.exception.*
import io.kuz.ecom.auth.domain.model.*
import org.springframework.beans.factory.annotation.Value
import java.util.UUID
import java.time.Instant

class AuthServiceImpl(
    private val verificationRepo: VerificationRepository,
    private val credentialsRepo: CredentialsRepository,
    private var authSessionsRepo: AuthSessionsRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenService: TokenService,
    @Value("\${app.verify.ttl}")
    private val verifySessionTTL: Long,
    @Value("\${app.verify.retry-timeout}")
    private val verifySessionRetryTimeout: Long,
): AuthService {

    override suspend fun initiateVerification(variant: VerificationVariant): VerificationSessionData {
        verificationRepo.readVerificationSession(variant)?.let {
            verificationRepo.deleteVerificationSession(it.id)
        }

        val data = VerificationSessionData(
            id = UUID.randomUUID().toString(),
            expectedCode = createCode(),
            ttl = Instant.now().plusSeconds(verifySessionTTL),
            retryTimeout = Instant.now().plusSeconds(verifySessionRetryTimeout),
            isConfirmed = false,
        )

        verificationRepo.createVerificationSession(data, variant)
        return data
    }

    override suspend fun resendVerificationCode(sessionId: String) {
        val data = readVerificationSession(sessionId)
        val now = Instant.now()

        if (data.retryTimeout > now) {
            throw TooEarlyException()
        }

        verificationRepo.updateVerificationSession(
            sessionId,
            expectedCode = createCode(),
            retryTimeout = now.plusSeconds(verifySessionRetryTimeout),
        )
    }

    override suspend fun confirmVerificationData(sessionId: String, code: String): Boolean {
        val data = readVerificationSession(sessionId)

        if (data.expectedCode == code) {
            verificationRepo.updateVerificationSession(sessionId, isConfirmed = true)
            return true
        }

        return false
    }

    override suspend fun signUp(sessionId: String, password: String) {
        val data = readVerificationSession(sessionId)

        if (!data.isConfirmed) {
            throw NotConfirmedException()
        }

        val variant = (verificationRepo.readVerificationSessionVariant(sessionId) as? VerificationVariant.Email) ?:
            throw IllegalStateException("Incorrect session")

        verificationRepo.deleteVerificationSession(sessionId)

        credentialsRepo.createRecord(
            CredentialsRecordModel(
                userId = UUID.randomUUID(),
                credentials = Credentials.Local(
                    passwordHash = passwordHasher.hash(password),
                    email = variant.value
                )
            )
        )
    }

    override suspend fun login(email: String, password: String): SessionTokensData {
        val record = credentialsRepo.readRecord(AuthProvider.LOCAL, email) ?: throw NotFoundException()
        val credentials = record.credentials as? Credentials.Local ?: throw IllegalStateException()
        if (passwordHasher.verify(password, credentials.passwordHash)) {
            val userId = record.userId.toString()
            val sessionId = UUID.randomUUID().toString()
            authSessionsRepo.create(userId, sessionId)
            return SessionTokensData(
                userId = userId,
                accessToken = tokenService.createAccessToken(
                    userId = userId,
                    sessionId = sessionId,
                ),
                refreshToken = tokenService.createRefreshToken(
                    userId = userId,
                    sessionId = sessionId,
                ),
            )
        } else {
            throw InvalidCredentialsException()
        }
    }

    override suspend fun logout(accessToken: String) {
        val meta = tokenService.parseMetadata(accessToken)
        authSessionsRepo.delete(meta.userId, meta.sessionId)
    }

    private fun readVerificationSession(sessionId: String): VerificationSessionData {
        val data = verificationRepo.readVerificationSession(sessionId) ?: throw NotFoundException()

        if (data.ttl <= Instant.now()) {
            throw ExpiredException()
        }

        return data
    }

    private fun createCode(): String {
        return (0..999_999)
            .random()
            .toString()
            .padStart(6, '0')
    }
}