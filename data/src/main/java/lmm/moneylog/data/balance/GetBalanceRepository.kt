package lmm.moneylog.data.balance

import kotlinx.coroutines.flow.Flow

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<Double>>
    suspend fun getAllValuesByAccount(accountId: Int): List<Double>
}
