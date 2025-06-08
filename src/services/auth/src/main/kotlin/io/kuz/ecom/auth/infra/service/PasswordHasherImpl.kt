package io.kuz.ecom.auth.infra.service

import at.favre.lib.crypto.bcrypt.BCrypt
import io.kuz.ecom.auth.domain.PasswordHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("prod")
class PasswordHasherImpl: PasswordHasher {

    override suspend fun hash(
        password: String
    ): String = withContext(Dispatchers.IO) {
        BCrypt
            .withDefaults()
            .hashToString(12, password.toCharArray())
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

@Service
@Profile("local")
class PasswordHasherImplLocal: PasswordHasher {

    override suspend fun hash(
        password: String
    ): String = withContext(Dispatchers.IO) {
        BCrypt
            .withDefaults()
            .hashToString(3, password.toCharArray())
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