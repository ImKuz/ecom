package io.kuz.ecom.auth.app.service

import io.kuz.ecom.auth.app.service.extension.readChecked
import io.kuz.ecom.auth.domain.*
import io.kuz.ecom.auth.domain.exception.*
import io.kuz.ecom.auth.domain.model.*
import io.kuz.ecom.auth.domain.repository.AuthSessionsRepository
import io.kuz.ecom.auth.domain.repository.CredentialsRepository
import io.kuz.ecom.auth.domain.repository.VerificationRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthServiceImpl(
    private val verificationRepo: VerificationRepository,
    private val credentialsRepo: CredentialsRepository,
    private var authSessionsRepo: AuthSessionsRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenService: TokenService,
): AuthService {

    override suspend fun signUp(sessionId: String, password: String): SessionTokensData {
        val data = verificationRepo.readChecked(sessionId)

        if (!data.isConfirmed) throw NotConfirmedException()

        val variant = (verificationRepo.readVerificationSessionVariant(sessionId) as? VerificationVariant.Email) ?:
            throw IllegalStateException("Incorrect session")

        verificationRepo.deleteVerificationSession(sessionId)

        val userId = UUID.randomUUID()

        credentialsRepo.createRecord(
            CredentialsRecordModel(
                userId = userId,
                credentials = Credentials.Local(
                    passwordHash = passwordHasher.hash(password),
                    email = variant.value
                )
            )
        )

        return createAuthSession(userId.toString())
    }

    override suspend fun login(email: String, password: String): SessionTokensData {
        val record = credentialsRepo.readRecord(AuthProvider.LOCAL, email) ?: throw NotFoundException()
        val credentials = record.credentials as? Credentials.Local ?: throw IllegalStateException()
        if (!passwordHasher.verify(password, credentials.passwordHash)) throw InvalidCredentialsException()

        return createAuthSession(record.userId.toString())
    }

    private suspend fun createAuthSession(userId: String): SessionTokensData {
        val sessionId = UUID.randomUUID().toString()
        authSessionsRepo.create(userId, sessionId)
        return SessionTokensData(
            userId = userId,
            accessToken = tokenService.createAccessToken(userId, sessionId),
            refreshToken = tokenService.createRefreshToken(userId, sessionId),
        )
    }

    override suspend fun logout(accessToken: String) {
        val meta = tokenService.parseMetadata(accessToken)
        authSessionsRepo.delete(meta.userId, meta.sessionId)
    }
}