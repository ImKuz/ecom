package io.kuz.ecom.auth.infra.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.kuz.ecom.auth.domain.TokenService
import io.kuz.ecom.auth.domain.exception.ExpiredException
import io.kuz.ecom.auth.domain.exception.InvalidCredentialsException
import io.kuz.ecom.auth.domain.model.TokenMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant

@Service
class TokenServiceImpl(
    @Value("\${app.token.publicKeyPath}")
    private val publicKeyPath: String,
    @Value("\${app.token.privateKeyPath}")
    private val privateKeyPath: String,
    @Value("\${app.token.access-ttl}")
    private val accessTTL: Long,
    @Value("\${app.token.refresh-ttl}")
    private val refreshTTL: Long,
): TokenService {

    private val privateKey = loadPrivateKey(privateKeyPath)
    private val publicKey = loadPublicKey(publicKeyPath)

    override fun createAccessToken(
        userId: String,
        sessionId: String
    ): String = createToken(
        userId = userId,
        sessionId = sessionId,
        ttl = accessTTL,
    )

    override fun createRefreshToken(
        userId: String,
        sessionId: String
    ): String = createToken(
        userId = userId,
        sessionId = sessionId,
        ttl = refreshTTL,
    )

    override fun parseMetadata(tokenString: String): TokenMetadata {
        try {
            val algorithm = Algorithm.RSA256(publicKey)
            val verifier = JWT.require(algorithm).build()
            val jwt: DecodedJWT = verifier.verify(tokenString)
            return TokenMetadata(
                userId = jwt.getClaim("user_id").asString(),
                sessionId = jwt.getClaim("session_id").asString(),
                createdAt = jwt.issuedAtAsInstant,
                expiresAt = jwt.expiresAtAsInstant,
            )
        } catch (e: Exception) {
            throw InvalidCredentialsException("The token is invalid: ${e.message}")
        }
    }

    private fun createToken(
        userId: String,
        sessionId: String,
        ttl: Long,
    ): String {
        val algorithm = Algorithm.RSA256(publicKey, privateKey)
        val now = Instant.now()
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("user_id", userId)
            .withClaim("session_id", sessionId)
            .withIssuedAt(now)
            .withExpiresAt(now.plusSeconds(ttl))
            .sign(algorithm)
    }

    private fun loadPrivateKey(path: String): RSAPrivateKey {
        val keyBytes = Files.readAllBytes(Paths.get(path))
        val keyString = String(keyBytes)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyString)
        val spec = PKCS8EncodedKeySpec(decoded)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(spec) as RSAPrivateKey
    }

    private fun loadPublicKey(path: String): RSAPublicKey {
        val keyBytes = Files.readAllBytes(Paths.get(path))
        val keyString = String(keyBytes)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyString)
        val spec = X509EncodedKeySpec(decoded)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec) as RSAPublicKey
    }
}