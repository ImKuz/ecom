package io.kuz.spring.helpers

import org.springframework.core.env.Environment

object ProfileUtils {

    @JvmStatic
    fun isProfileActive(env: Environment, profile: String): Boolean =
        env.activeProfiles.contains(profile)

    @JvmStatic
    fun isLocal(env: Environment): Boolean = isProfileActive(env, "local")

    @JvmStatic
    fun isProd(env: Environment): Boolean = isProfileActive(env, "prod")
}