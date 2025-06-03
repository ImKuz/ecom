package io.kuz.ecom.auth.domain.exception

class InvalidCredentialsException(
    override var message: String? = null
): Exception(message)