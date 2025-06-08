package io.kuz.ecom.auth.infra.repository

import com.fasterxml.jackson.databind.introspect.AnnotatedClass.Creators
import io.kuz.ecom.auth.domain.repository.CredentialsRepository
import io.kuz.ecom.auth.domain.model.AuthProvider
import io.kuz.ecom.auth.domain.model.Credentials
import io.kuz.ecom.auth.domain.model.CredentialsRecordModel
import io.kuz.ecom.auth.infra.db.table.CredentialsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class CredentialsRepositoryImpl: CredentialsRepository {

    override suspend fun createRecord(record: CredentialsRecordModel) {
        withContext(Dispatchers.IO) {
            transaction {
                CredentialsTable.insert { row ->
                    row[userId] = record.userId
                    row[provider] = record.authProvider.name
                    when (record.credentials) {
                        is Credentials.Local -> {
                            row[identifier] = record.credentials.email
                            row[passwordHash] = record.credentials.passwordHash
                        }
                    }
                }
            }
        }
    }

    override suspend fun readRecord(
        provider: AuthProvider,
        identifier: String
    ): CredentialsRecordModel? = withContext(Dispatchers.IO) {
        transaction {
            CredentialsTable
                .select(
                    CredentialsTable.userId,
                    CredentialsTable.provider,
                    CredentialsTable.identifier,
                    CredentialsTable.passwordHash
                )
                .where {
                    (CredentialsTable.provider eq provider.name)
                        .and(CredentialsTable.identifier eq identifier)
                }
                .map {
                    CredentialsRecordModel(
                        userId = it[CredentialsTable.userId],
                        credentials = when (it[CredentialsTable.provider]) {
                            AuthProvider.LOCAL.name -> Credentials.Local(
                                email = it[CredentialsTable.identifier],
                                passwordHash = it[CredentialsTable.passwordHash]
                            )
                            else -> throw IllegalStateException("Unknown provider")
                        }
                    )
                }
                .singleOrNull()
        }
    }
}