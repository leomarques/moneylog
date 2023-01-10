package lmm.moneylog.domain.getbalance

import kotlinx.coroutines.flow.Flow

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<Double>>
}
