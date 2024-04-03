package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.model.TransactionBalance

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<TransactionBalance>>

    suspend fun getAllValuesByAccount(accountId: Int): List<Double>
}
