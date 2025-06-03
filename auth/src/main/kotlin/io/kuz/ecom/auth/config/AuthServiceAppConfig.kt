package io.kuz.ecom.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kuz.ecom.auth.config.AuthApplicationProperties
import io.kuz.ecom.auth.config.AuthDBProperties
import io.kuz.ecom.auth.infra.db.table.CredentialsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import javax.sql.DataSource


@Configuration
@EnableConfigurationProperties(
    value = [
        AuthDBProperties::class,
        AuthApplicationProperties::class
    ]
)
@ComponentScan("io.kuz.ecom.auth")
class AuthServiceAppConfig(
    private val dbProps: AuthDBProperties,
) {
    @Bean
    fun dataSource(): DataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = dbProps.jdbcUrl
            driverClassName = "org.postgresql.Driver"
            username = dbProps.username
            password = dbProps.password
            maximumPoolSize = 10
        }
    )

    @Bean
    fun reactiveStringRedisTemplate(factory: ReactiveRedisConnectionFactory) =
        ReactiveStringRedisTemplate(factory)

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModules(
                JavaTimeModule(),
                KotlinModule.Builder().build()
            )

    @Bean
    fun database(dataSource: DataSource): Database {
        val db = Database.connect(dataSource)

        transaction {
            addLogger(StdOutSqlLogger)
        }

        transaction {
            SchemaUtils.create(
                CredentialsTable
            )
        }

        return db
    }
}