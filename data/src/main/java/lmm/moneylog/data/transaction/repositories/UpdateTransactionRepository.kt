package lmm.moneylog.data.transaction.repositories

import lmm.moneylog.data.transaction.Transaction

interface UpdateTransactionRepository {
    suspend fun update(transaction: Transaction)
}
