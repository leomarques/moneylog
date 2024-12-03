package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.balance.model.TransactionBalance

interface GetBalanceRepository {
    fun getTransactions(): Flow<List<TransactionBalance>>

    suspend fun getAllValuesByAccount(accountId: Int): List<Double>
}
