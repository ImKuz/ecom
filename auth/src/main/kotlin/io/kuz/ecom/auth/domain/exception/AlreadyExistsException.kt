package io.kuz.ecom.auth.domain.exception

class AlreadyExistsException: Exception(
    "The initiated process/session already exists for the resource"
)