package lmm.moneylog.data.transaction.repositories

import lmm.moneylog.data.transaction.Transaction

interface GetTransactionRepository {
    suspend fun getTransactionById(id: Int): Transaction?
}
