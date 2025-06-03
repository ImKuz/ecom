package io.kuz.ecom.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "db")
class AuthDBProperties {
    lateinit var jdbcUrl: String
    lateinit var username: String
    lateinit var password: String
}