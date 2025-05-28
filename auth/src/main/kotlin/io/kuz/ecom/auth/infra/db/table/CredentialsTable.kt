package io.kuz.ecom.auth.infra.db.table

import org.jetbrains.exposed.dao.id.IntIdTable

object CredentialsTable: IntIdTable("credentials") {

    val userId = uuid("user_id")

    val provider = varchar("provider", 64)

    val identifier = varchar("identifier", 255)

    val passwordHash = text("password_hash")
}