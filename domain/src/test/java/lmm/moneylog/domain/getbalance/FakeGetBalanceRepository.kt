package lmm.moneylog.domain.getbalance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeGetBalanceRepository: GetBalanceRepository {
    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return listOf(listOf(1.0, 2.0, 3.0)).asFlow()
    }
}
