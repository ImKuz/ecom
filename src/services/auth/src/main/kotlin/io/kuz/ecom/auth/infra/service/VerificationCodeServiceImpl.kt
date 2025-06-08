package io.kuz.ecom.auth.infra.service

import io.kuz.ecom.auth.domain.VerificationCodeService
import io.kuz.spring.helpers.ProfileUtils
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class VerificationCodeServiceImpl(
    private val env: Environment,
): VerificationCodeService {

    override fun createCode(): String {
        return if (ProfileUtils.isProd(env)) {
            (0..999_999)
                .random()
                .toString()
                .padStart(6, '0')
        } else {
            "000000"
        }
    }
}