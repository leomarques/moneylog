package lmm.moneylog.data.transaction.repositories.interfaces

import lmm.moneylog.data.transaction.model.Transaction

interface UpdateTransactionRepository {
    suspend fun update(transaction: Transaction)
}
