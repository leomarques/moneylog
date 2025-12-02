package lmm.moneylog.data.graphs.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import lmm.moneylog.data.graphs.model.NetWorthPoint
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository

private const val NET_WORTH_HISTORY_MONTHS = 24
private const val FIRST_MONTH = 1
private const val LAST_MONTH = 12
private const val MONTH_ABBREVIATION_LENGTH = 3
private const val YEAR_SUFFIX_LENGTH = 2

/**
 * Interactor for getting net worth history over time
 * Calculates cumulative balance at the end of each month
 *
 * @property getTransactionsRepository Repository for fetching transactions
 * @property domainTimeRepository Repository for time-related operations
 */
class GetNetWorthHistoryInteractor(
    private val getTransactionsRepository: GetTransactionsRepository,
    private val domainTimeRepository: DomainTimeRepository
) {
    /**
     * Get net worth history for the past 24 months from the specified month
     *
     * @param currentMonth The current month (1-12)
     * @param currentYear The current year
     * @return Flow of list of NetWorthPoint for the past 24 months
     */
    fun getNetWorthHistory(
        currentMonth: Int,
        currentYear: Int
    ): Flow<List<NetWorthPoint>> {
        // Calculate 24 months of data (2 years)
        val monthlyData = mutableListOf<Pair<Int, Int>>() // Pair of (month, year)

        var month = currentMonth
        var year = currentYear

        // Get past 24 months including current
        repeat(NET_WORTH_HISTORY_MONTHS) {
            monthlyData.add(0, Pair(month, year)) // Add to beginning to maintain order

            // Move to previous month
            if (month == FIRST_MONTH) {
                month = LAST_MONTH
                year--
            } else {
                month--
            }
        }

        // Create flows for each month
        val flows =
            monthlyData.map { (m, y) ->
                getTransactionsRepository.getAllTransactions(month = m, year = y)
            }

        // Combine all flows
        return combine(flows) { transactionArrays ->
            var cumulativeBalance = 0.0

            monthlyData.mapIndexed { index, (m, y) ->
                // Add this month's paid transactions to cumulative balance
                val monthTransactions = transactionArrays[index]
                // Only add transactions from accounts (paid transactions)
                cumulativeBalance +=
                    monthTransactions
                        .filter { it.accountId != null }
                        .sumOf { it.value }

                val shortMonthName =
                    domainTimeRepository
                        .getMonthName(m)
                        .take(MONTH_ABBREVIATION_LENGTH)
                        .replaceFirstChar { it.titlecase() }

                // For year boundaries, show "Mon/YY" format
                val isYearBoundary = m == FIRST_MONTH || m == LAST_MONTH
                val isFirstOrLast = index == 0 || index == monthlyData.size - 1
                val displayName =
                    if (isYearBoundary || isFirstOrLast) {
                        "$shortMonthName/${y.toString().takeLast(YEAR_SUFFIX_LENGTH)}"
                    } else {
                        shortMonthName
                    }

                NetWorthPoint(
                    month = m,
                    year = y,
                    monthName = displayName,
                    netWorth = cumulativeBalance
                )
            }
        }
    }
}
