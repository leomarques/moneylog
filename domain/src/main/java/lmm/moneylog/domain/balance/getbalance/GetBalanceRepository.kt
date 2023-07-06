package lmm.moneylog.domain.balance.getbalance

import kotlinx.coroutines.flow.Flow

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<Double>>
}
