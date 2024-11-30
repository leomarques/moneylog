package lmm.moneylog.data.balance.interactors

import kotlinx.coroutines.flow.map
import lmm.moneylog.data.balance.model.BalanceModel
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalanceInteractor(private val repository: GetBalanceRepository) {
    fun execute(
        monthNumber: Int,
        yearNumber: Int
    ) = repository.getAllPaidTransactionsValues().map { allPaidTransactions ->
        val currentTransactions =
            allPaidTransactions.filter {
                it.month == monthNumber && it.year == yearNumber
            }

        val credit =
            currentTransactions
                .filter { it.value > 0 }
                .sumOf { it.value }
        val debt =
            currentTransactions
                .filter { it.value < 0 }
                .sumOf { it.value }

        BalanceModel(
            total = currentTransactions.sumOf { it.value },
            credit = credit,
            debt = debt
        )
    }
}
