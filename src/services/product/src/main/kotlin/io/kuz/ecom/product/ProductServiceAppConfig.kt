package io.kuz.ecom.product

import io.kuz.ecom.product.infra.db.table.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ComponentScan("io.kuz.ecom.product")
class ProductServiceAppConfig {

    @Bean
    fun database(dataSource: DataSource): Database {
        val db = Database.connect(dataSource)

        transaction {
            addLogger(StdOutSqlLogger)
        }

        transaction {
            SchemaUtils.create(
                ProductCategoriesTable,
                ProductTable,
                ProductAttributesTable,
                ProductAttributeOptionsTable,
                ProductAttributeValuesTable,
            )
        }

        return db
    }
}