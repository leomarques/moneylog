package lmm.moneylog.domain.balance.getbalance.fakerepos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import lmm.moneylog.domain.balance.getbalance.GetBalanceRepository

class FakeGetBalanceRepository1 : GetBalanceRepository {
    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return listOf(listOf(1.0, 2.0, 3.0)).asFlow()
    }
}
