package lmm.moneylog.domain.getbalance.fakerepos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import lmm.moneylog.domain.getbalance.GetBalanceRepository

class FakeGetBalanceRepository3 : GetBalanceRepository {
    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return listOf(listOf(-10.0, -20.0)).asFlow()
    }
}