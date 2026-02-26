package lmm.moneylog.data.graphs.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.graphs.model.NetWorthPoint
import lmm.moneylog.data.time.repositories.DomainTimeRepository

private const val NET_WORTH_HISTORY_MONTHS = 24
private const val FIRST_MONTH = 1
private const val LAST_MONTH = 12
private const val MONTH_ABBREVIATION_LENGTH = 3
private const val YEAR_SUFFIX_LENGTH = 2

/**
 * Interactor for getting net worth history over time
 * Calculates cumulative balance at the end of each month
 *
 * @property getBalanceRepository Repository for fetching all transactions
 * @property domainTimeRepository Repository for time-related operations
 */
class GetNetWorthHistoryInteractor(
    private val getBalanceRepository: GetBalanceRepository,
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

        return getBalanceRepository.getTransactions().map { allTransactions ->
            monthlyData.map { (m, y) ->
                val cumulativeBalance =
                    allTransactions
                        .filter { transaction ->
                            transaction.accountId != null &&
                                    (transaction.year < y || (transaction.year == y && transaction.month <= m))
                        }.sumOf { it.value }

                val shortMonthName =
                    domainTimeRepository
                        .getMonthName(m)
                        .take(MONTH_ABBREVIATION_LENGTH)
                        .replaceFirstChar { it.titlecase() }

                // For year boundaries, show "Mon/YY" format
                val isYearBoundary = m == FIRST_MONTH || m == LAST_MONTH
                val isFirstOrLast =
                    monthlyData.indexOf(Pair(m, y)) == 0 ||
                            monthlyData.indexOf(Pair(m, y)) == monthlyData.size - 1
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
