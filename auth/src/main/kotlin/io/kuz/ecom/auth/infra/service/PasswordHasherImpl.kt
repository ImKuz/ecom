package io.kuz.ecom.auth.infra.service

import at.favre.lib.crypto.bcrypt.BCrypt
import io.kuz.ecom.auth.domain.PasswordHasher
import org.springframework.stereotype.Service

@Service
class PasswordHasherImpl: PasswordHasher {

    override fun hash(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    override fun verify(password: String, hash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hash)
        return result.verified
    }
}