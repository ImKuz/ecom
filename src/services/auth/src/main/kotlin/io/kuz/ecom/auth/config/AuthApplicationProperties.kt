package io.kuz.ecom.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "app")
@Validated
data class AuthApplicationProperties(
    val port: Int
)

