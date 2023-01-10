package lmm.moneylog.domain

import kotlinx.coroutines.flow.Flow

interface GetBalanceRepository {
    fun getAllTransactionsValues(): Flow<List<Double>>
}
