package lmm.moneylog.data.graphs.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import lmm.moneylog.data.graphs.model.MonthlyTotal
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import kotlin.math.absoluteValue

private const val MONTHLY_TOTALS_MONTHS = 12
private const val FIRST_MONTH = 1
private const val LAST_MONTH = 12
private const val MONTH_ABBREVIATION_LENGTH = 3

/**
 * Interactor for getting monthly income and expense totals
 * Provides data for bar charts showing income vs expenses over time
 *
 * @property getTransactionsRepository Repository for fetching transactions
 * @property domainTimeRepository Repository for time-related operations
 */
class GetMonthlyTotalsInteractor(
    private val getTransactionsRepository: GetTransactionsRepository,
    private val domainTimeRepository: DomainTimeRepository
) {
    /**
     * Get monthly totals for the past 12 months from the specified month
     *
     * @param currentMonth The current month (1-12)
     * @param currentYear The current year
     * @return Flow of list of MonthlyTotal for the past 12 months
     */
    fun getMonthlyTotals(
        currentMonth: Int,
        currentYear: Int
    ): Flow<List<MonthlyTotal>> {
        // Calculate 12 months of data
        val monthlyData = mutableListOf<Pair<Int, Int>>() // Pair of (month, year)

        var month = currentMonth
        var year = currentYear

        // Get past 12 months including current
        repeat(MONTHLY_TOTALS_MONTHS) {
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
            monthlyData.mapIndexed { index, (m, y) ->
                val monthTransactions = transactionArrays[index]

                val income =
                    monthTransactions
                        .filter { it.value > 0 }
                        .sumOf { it.value.absoluteValue }

                val expenses =
                    monthTransactions
                        .filter { it.value < 0 }
                        .sumOf { it.value.absoluteValue }

                MonthlyTotal(
                    month = m,
                    year = y,
                    monthName =
                        domainTimeRepository
                            .getMonthName(m)
                            .take(MONTH_ABBREVIATION_LENGTH)
                            .replaceFirstChar { it.titlecase() },
                    income = income,
                    expenses = expenses
                )
            }
        }
    }
}
