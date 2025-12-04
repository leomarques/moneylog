package lmm.moneylog.data.graphs.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.graphs.model.CategoryAmount
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import kotlin.math.absoluteValue

/**
 * Interactor for getting transactions grouped by category
 * Combines transaction and category data to provide category-wise totals
 *
 * @property getTransactionsRepository Repository for fetching transactions
 * @property getCategoriesRepository Repository for fetching categories
 */
class GetTransactionsByCategoryInteractor(
    private val getTransactionsRepository: GetTransactionsRepository,
    private val getCategoriesRepository: GetCategoriesRepository
) {
    /**
     * Get income transactions grouped by category for a specific month
     * Includes uncategorized transactions as a separate entry
     *
     * @param month The month number (1-12)
     * @param year The year
     * @return Flow of list of CategoryAmount for income transactions
     */
    fun getIncomeByCategory(
        month: Int,
        year: Int
    ): Flow<List<CategoryAmount>> =
        getTransactionsByCategory(
            transactionsFlow = getTransactionsRepository.getIncomeTransactions(month, year),
            isIncome = true
        )

    /**
     * Get expense transactions grouped by category for a specific month
     * Includes uncategorized transactions as a separate entry
     *
     * @param month The month number (1-12)
     * @param year The year
     * @return Flow of list of CategoryAmount for expense transactions
     */
    fun getExpensesByCategory(
        month: Int,
        year: Int
    ): Flow<List<CategoryAmount>> =
        getTransactionsByCategory(
            transactionsFlow = getTransactionsRepository.getOutcomeTransactions(month, year),
            isIncome = false
        )

    /**
     * Get transactions grouped by category
     * Includes uncategorized transactions as a separate entry
     *
     * @param transactionsFlow Flow of transactions to group
     * @param isIncome Whether to filter for income or expense categories
     * @return Flow of list of CategoryAmount
     */
    private fun getTransactionsByCategory(
        transactionsFlow: Flow<List<Transaction>>,
        isIncome: Boolean
    ): Flow<List<CategoryAmount>> =
        combine(
            transactionsFlow,
            getCategoriesRepository.getCategories()
        ) { transactions, categories ->
            // Filter categories based on type (income or expense)
            val categoryMap = categories.filter { it.isIncome == isIncome }.associateBy { it.id }

            // Separate categorized and uncategorized transactions
            val (categorizedTransactions, uncategorizedTransactions) = transactions.partition { it.categoryId != null }

            // Group categorized transactions by category and sum amounts
            val categoryTotals =
                categorizedTransactions
                    .filter { categoryMap.containsKey(it.categoryId) }
                    .groupBy { it.categoryId!! }
                    .mapValues { (_, trans) ->
                        trans.sumOf { it.value.absoluteValue }
                    }

            // Calculate uncategorized total
            val uncategorizedTotal = uncategorizedTransactions.sumOf { it.value.absoluteValue }

            // Calculate grand total for percentage calculation
            val totalAmount = categoryTotals.values.sum() + uncategorizedTotal

            // Convert categorized transactions to CategoryAmount
            val categorizedAmounts =
                categoryTotals
                    .mapNotNull { (categoryId, amount) ->
                        val category = categoryMap[categoryId]
                        if (category != null && amount > 0) {
                            CategoryAmount(
                                categoryId = categoryId,
                                categoryName = category.name,
                                categoryColor = category.color,
                                totalAmount = amount,
                                percentage = if (totalAmount > 0) (amount / totalAmount * 100).toFloat() else 0f
                            )
                        } else {
                            null
                        }
                    }

            // Add uncategorized entry if there are any uncategorized transactions
            val result =
                if (uncategorizedTotal > 0) {
                    categorizedAmounts +
                        CategoryAmount(
                            categoryId = null,
                            categoryName = "Uncategorized",
                            categoryColor = null,
                            totalAmount = uncategorizedTotal,
                            percentage = if (totalAmount > 0) (uncategorizedTotal / totalAmount * 100).toFloat() else 0f
                        )
                } else {
                    categorizedAmounts
                }

            result.sortedByDescending { it.totalAmount }
        }
}
