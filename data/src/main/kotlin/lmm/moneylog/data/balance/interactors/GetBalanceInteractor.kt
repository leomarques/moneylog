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

        // Calculate cumulative balance up to and including the specified month/year
        // Credit cards use paidMonth/paidYear, so they appear when money left the account
        // Unpaid transactions are filtered at SQL level (accountId IS NOT NULL)
        val cumulativeBalance =
            allTransactions
                .filter {
                    it.year < yearNumber || (it.year == yearNumber && it.month <= monthNumber)
                }.sumOf { it.value }

        BalanceModel(
            total = cumulativeBalance,
            credit = credit,
            debt = -debt
        )
    }
}
