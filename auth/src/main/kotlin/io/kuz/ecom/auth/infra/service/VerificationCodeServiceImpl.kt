package io.kuz.ecom.auth.infra.service

import io.kuz.ecom.auth.domain.VerificationCodeService

class VerificationCodeServiceImpl: VerificationCodeService {

    override fun createCode(): String {
        return (0..999_999)
            .random()
            .toString()
            .padStart(6, '0')
    }
}