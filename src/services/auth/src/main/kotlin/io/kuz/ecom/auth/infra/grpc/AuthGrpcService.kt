package io.kuz.ecom.auth.infra.grpc

import com.google.protobuf.Empty
import io.grpc.Status
import io.kuz.ecom.auth.domain.AuthService
import io.kuz.ecom.auth.domain.SessionVerificationService
import io.kuz.ecom.auth.domain.exception.ExpiredException
import io.kuz.ecom.auth.domain.exception.InvalidCredentialsException
import io.kuz.ecom.auth.domain.exception.NotFoundException
import io.kuz.ecom.auth.domain.model.SessionTokensData
import io.kuz.ecom.auth.domain.model.VerificationVariant
import io.kuz.ecom.proto.auth.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthGrpcService(
    private val authService: AuthService,
    private val sessionVerificationService: SessionVerificationService,
): AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    private val logger = LoggerFactory.getLogger(AuthGrpcService::class.java)

    override suspend fun signUpInitiateVerification(
        request: SignUpInitiateVerificationRequest
    ): SignUpInitiateVerificationResponse = perform {
        val data = sessionVerificationService.initiateVerification(
            variant = when (request.provider) {
                AuthProvider.LOCAL -> VerificationVariant.Email(
                    request.identifier
                )
                else -> {
                    val msg = "Unknown auth provider: ${request.provider}"
                    logger.warn("signUpInitiateVerification: $msg")
                    throw Status.INVALID_ARGUMENT
                        .withDescription(msg)
                        .asRuntimeException()
                }
            }
        )

        return SignUpInitiateVerificationResponse
            .newBuilder()
            .setSessionId(data.id)
            .setRetryTimeout(data.retryTimeout.toString())
            .setTtl(data.ttl.toString())
            .build()
    }

    override suspend fun signUpConfirmVerification(
        request: SignUpConfirmVerificationRequest
    ): Empty = perform {
        sessionVerificationService.confirmVerificationData(request.sessionId, request.code)
        return Empty.newBuilder().build()
    }

    override suspend fun signUpResendCode(request: SignUpResendCodeRequest): Empty = perform {
        sessionVerificationService.resendVerificationCode(request.sessionId)
        return Empty.newBuilder().build()
    }

    override suspend fun emailSignUp(request: EmailSignUpRequest): AuthSessionResponse = perform {
        return mapSessionData(
            authService.signUp(
                sessionId = request.sessionId,
                password = request.password
            )
        )
    }

    override suspend fun emailLogin(request: EmailLoginRequest): AuthSessionResponse = perform {
        return mapSessionData(
            authService.login(
                request.email,
                request.password
            )
        )
    }

    override suspend fun logout(request: LogoutRequest): Empty = perform {
        authService.logout(request.accessToken)
        return Empty.newBuilder().build()
    }

    override suspend fun validateToken(request: ValidateTokenRequest): Empty = perform {
        authService.verifyToken(request.token)
        return Empty.newBuilder().build()
    }

    private inline fun <T> perform(block: () -> T): T {
        return try {
            block()
        } catch (ex: Exception) {
            throw mapException(ex).asRuntimeException()
        }
    }

    private fun mapException(ex: Exception): Status {
        return when (ex) {
            is ExpiredException -> Status.INVALID_ARGUMENT.withDescription(ex.message)
            is NotFoundException -> Status.NOT_FOUND.withDescription(ex.message)
            is InvalidCredentialsException -> Status.PERMISSION_DENIED.withDescription(ex.message)
            else -> Status.INTERNAL.withDescription(ex.message)
        }
    }

    private fun mapSessionData(data: SessionTokensData): AuthSessionResponse {
        return AuthSessionResponse
            .newBuilder()
            .setUserId(data.userId)
            .setAccessToken(data.accessToken)
            .setRefreshToken(data.refreshToken)
            .build()
    }
}