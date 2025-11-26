package lmm.moneylog.data.transaction.repositories.interfaces

import lmm.moneylog.data.transaction.model.Transaction

interface AddTransactionRepository {
    suspend fun save(transaction: Transaction): Long
}
