package lmm.moneylog.data.balance.interactors

import kotlinx.coroutines.flow.map
import lmm.moneylog.data.balance.model.BalanceModel
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalanceInteractor(
    private val repository: GetBalanceRepository
) {
    fun execute(
        monthNumber: Int,
        yearNumber: Int
    ) = repository.getTransactions().map { allTransactions ->
        val currentTransactions =
            allTransactions.filter {
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

        val paidTransactions =
            allTransactions.filter {
                it.accountId != null &&
                        (it.year < yearNumber || (it.year == yearNumber && it.month <= monthNumber))
            }.sumOf { it.value }

        BalanceModel(
            total = paidTransactions,
            credit = credit,
            debt = -debt
        )
    }
}
