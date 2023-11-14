package lmm.moneylog.data.balance.interactors

import kotlinx.coroutines.flow.map
import lmm.moneylog.data.balance.model.BalanceModel
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalanceInteractor(private val repository: GetBalanceRepository) {

    fun execute(
        monthNumber: Int,
        yearNumber: Int
    ) = repository.getAllTransactionsValues().map { values ->
        val currentTransactions = values.filter {
            it.month == monthNumber && it.year == yearNumber
        }

        val credit = currentTransactions
            .filter { it.value > 0 }
            .sumOf { it.value }
        val debt = currentTransactions
            .filter { it.value < 0 }
            .sumOf { it.value }

        BalanceModel(
            total = values.sumOf { it.value },
            credit = credit,
            debt = debt
        )
    }
}
