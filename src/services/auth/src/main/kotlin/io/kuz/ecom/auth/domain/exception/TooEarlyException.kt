package io.kuz.ecom.auth.domain.exception

class TooEarlyException: Exception(
    "The invoked action is not allowed yet"
)