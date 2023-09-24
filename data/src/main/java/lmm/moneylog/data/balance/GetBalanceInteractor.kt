package lmm.moneylog.data.balance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBalanceInteractor(private val repository: GetBalanceRepository) {

    fun execute(): Flow<BalanceModel> {
        val valuesFlow = repository.getAllTransactionsValues()

        val balanceModel: Flow<BalanceModel> = valuesFlow.map { values ->
            var credit = 0.0
            var debt = 0.0

            for (value in values) {
                if (value < 0) {
                    debt += value
                } else {
                    credit += value
                }
            }

            val total = credit + debt

            BalanceModel(
                total,
                credit,
                if (debt < 0) {
                    -debt
                } else {
                    debt
                }
            )
        }

        return balanceModel
    }
}
