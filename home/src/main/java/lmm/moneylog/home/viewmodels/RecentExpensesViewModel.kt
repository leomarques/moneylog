package lmm.moneylog.home.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.home.models.CategoryExpense
import lmm.moneylog.home.models.ExpensesSummary
import lmm.moneylog.ui.extensions.formatForRs
import kotlin.math.absoluteValue

private const val TOP_CATEGORIES_LIMIT = 5

/**
 * ViewModel for the recent expenses card
 * Provides current month's total expenses and list of recent transactions
 *
 * @property getTransactionsRepository Repository for fetching transactions
 * @property getCategoriesRepository Repository for fetching categories
 * @property domainTimeRepository Repository for time-related operations
 */
class RecentExpensesViewModel(
    private val getTransactionsRepository: GetTransactionsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _expensesSummary = MutableStateFlow<ExpensesSummary?>(null)
    val expensesSummary: StateFlow<ExpensesSummary?> = _expensesSummary.asStateFlow()

    init {
        loadExpensesSummary()
    }

    private fun loadExpensesSummary() {
        viewModelScope.launch {
            val currentDomainTime = domainTimeRepository.getCurrentDomainTime()

            // Get both transactions and categories
            getTransactionsRepository
                .getOutcomeTransactions(
                    month = currentDomainTime.month,
                    year = currentDomainTime.year
                ).collect { transactions ->
                    val categories = getCategoriesRepository.getCategoriesSuspend()
                    val categoryMap = categories.filter { !it.isIncome }.associateBy { it.id }

                    // Calculate total expenses
                    val totalAmount = transactions.sumOf { it.value.absoluteValue }

                    // Group by category and calculate percentages
                    val categoryExpenses =
                        transactions
                            .filter { it.categoryId != null && categoryMap.containsKey(it.categoryId) }
                            .groupBy { it.categoryId!! }
                            .mapNotNull { (categoryId, trans) ->
                                val category = categoryMap[categoryId]
                                val amount = trans.sumOf { it.value.absoluteValue }

                                if (category != null && amount > 0) {
                                    CategoryExpense(
                                        categoryName = category.name,
                                        categoryColor = Color(category.color.toULong()),
                                        amount = amount,
                                        percentage =
                                            if (totalAmount > 0) {
                                                (amount / totalAmount * 100).toFloat()
                                            } else {
                                                0f
                                            }
                                    )
                                } else {
                                    null
                                }
                            }.sortedByDescending { it.amount }
                            .take(TOP_CATEGORIES_LIMIT)

                    _expensesSummary.value =
                        ExpensesSummary(
                            totalExpenses = totalAmount.formatForRs(),
                            totalAmount = totalAmount,
                            categoryExpenses = categoryExpenses
                        )
                }
        }
    }
}
