package lmm.moneylog.data.transaction.repositories

import lmm.moneylog.data.transaction.Transaction

interface AddTransactionRepository {
    suspend fun save(transaction: Transaction)
}
