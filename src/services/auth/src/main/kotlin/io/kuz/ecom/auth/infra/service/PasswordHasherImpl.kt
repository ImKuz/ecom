package io.kuz.ecom.auth.infra.service

import at.favre.lib.crypto.bcrypt.BCrypt
import io.kuz.ecom.auth.domain.PasswordHasher
import io.kuz.spring.helpers.ProfileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class PasswordHasherImpl(
    private val env: Environment
): PasswordHasher {

    override suspend fun hash(
        password: String
    ): String = withContext(Dispatchers.IO) {
        val cost = if (ProfileUtils.isProd(env)) 12 else 3

        BCrypt
            .withDefaults()
            .hashToString(cost, password.toCharArray())
    }

    override suspend fun verify(
        password: String,
        hash: String
    ): Boolean = withContext(Dispatchers.IO) {
        BCrypt
            .verifyer()
            .verify(password.toCharArray(), hash)
            .verified
    }
}