package io.kuz.ecom.product

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kuz.ecom.auth.infra.db.table.CredentialsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ConfigurationProperties(prefix = "db")
class AuthDBProperties {
    lateinit var jdbcUrl: String
    lateinit var driverClassName: String
    lateinit var username: String
    lateinit var password: String
}

@Configuration
@ComponentScan("io.kuz.ecom.auth")
class AuthServiceAppConfig(
    private val dbProps: AuthDBProperties,
) {
    @Bean
    fun dataSource(): DataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = dbProps.jdbcUrl
            driverClassName = dbProps.driverClassName
            username = dbProps.username
            password = dbProps.password
            maximumPoolSize = 10
        }
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