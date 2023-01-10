package lmm.moneylog.domain.getbalance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBalanceInteractor(private val repository: GetBalanceRepository) {

    fun execute(): Flow<BalanceModel> {
        val valuesFlow = repository.getAllTransactionsValues()

        val balanceModel: Flow<BalanceModel> = valuesFlow.map { values ->
            var credit = 0.0
            var debt = 0.0

            for (value in values) {
                if (value > 0)
                    credit += value
                else
                    debt += value
            }

            val total = credit + debt

            BalanceModel(
                total,
                credit,
                debt
            )
        }

        return balanceModel
    }
}
