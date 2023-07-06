package lmm.moneylog.domain.balance.getbalance.fakerepos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import lmm.moneylog.domain.balance.getbalance.GetBalanceRepository

class FakeGetBalanceRepository4 : GetBalanceRepository {
    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return listOf(emptyList<Double>()).asFlow()
    }
}
