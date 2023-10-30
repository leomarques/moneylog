package lmm.moneylog.data.balance

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.database.TransactionBalance

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<TransactionBalance>>
    suspend fun getAllValuesByAccount(accountId: Int): List<Double>
}
