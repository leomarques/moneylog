package lmm.moneylog.data.graphs.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.graphs.model.CategoryAmount
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
     *
     * @param month The month number (1-12)
     * @param year The year
     * @return Flow of list of CategoryAmount for income transactions
     */
    fun getIncomeByCategory(
        month: Int,
        year: Int
    ): Flow<List<CategoryAmount>> =
        combine(
            getTransactionsRepository.getIncomeTransactions(month, year),
            getCategoriesRepository.getCategories()
        ) { transactions, categories ->
            // Filter only income categories
            val categoryMap = categories.filter { it.isIncome }.associateBy { it.id }

            // Group transactions by category and sum amounts
            val categoryTotals =
                transactions
                    .filter { it.categoryId != null && categoryMap.containsKey(it.categoryId) }
                    .groupBy { it.categoryId!! }
                    .mapValues { (_, trans) ->
                        trans.sumOf { it.value.absoluteValue }
                    }

            val totalAmount = categoryTotals.values.sum()

            // Convert to CategoryAmount with percentages
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
                }.sortedByDescending { it.totalAmount }
        }

    /**
     * Get expense transactions grouped by category for a specific month
     *
     * @param month The month number (1-12)
     * @param year The year
     * @return Flow of list of CategoryAmount for expense transactions
     */
    fun getExpensesByCategory(
        month: Int,
        year: Int
    ): Flow<List<CategoryAmount>> =
        combine(
            getTransactionsRepository.getOutcomeTransactions(month, year),
            getCategoriesRepository.getCategories()
        ) { transactions, categories ->
            // Filter only expense categories
            val categoryMap = categories.filter { !it.isIncome }.associateBy { it.id }

            // Group transactions by category and sum amounts
            val categoryTotals =
                transactions
                    .filter { it.categoryId != null && categoryMap.containsKey(it.categoryId) }
                    .groupBy { it.categoryId!! }
                    .mapValues { (_, trans) ->
                        trans.sumOf { it.value.absoluteValue }
                    }

            val totalAmount = categoryTotals.values.sum()

            // Convert to CategoryAmount with percentages
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
                }.sortedByDescending { it.totalAmount }
        }
}
