package lmm.moneylog.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.graphs.interactors.GetTransactionsByCategoryInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.home.models.CategoryExpense
import lmm.moneylog.home.models.ExpensesSummary
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.extensions.toComposeColor

private const val TOP_CATEGORIES_LIMIT = 4

/**
 * ViewModel for the recent expenses card
 * Provides current month's total expenses and list of recent transactions
 *
 * @property getTransactionsByCategoryInteractor Interactor for fetching transactions grouped by category
 * @property domainTimeRepository Repository for time-related operations
 */
class RecentExpensesViewModel(
    private val getTransactionsByCategoryInteractor: GetTransactionsByCategoryInteractor,
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

            // Use interactor to get expenses grouped by category
            getTransactionsByCategoryInteractor
                .getExpensesByCategory(
                    month = currentDomainTime.month,
                    year = currentDomainTime.year
                ).collect { categoryAmounts ->
                    // Calculate total amount
                    val totalAmount = categoryAmounts.sumOf { it.totalAmount }

                    // Convert CategoryAmount to CategoryExpense and take top 5
                    val categoryExpenses =
                        categoryAmounts
                            .take(TOP_CATEGORIES_LIMIT)
                            .map { categoryAmount ->
                                CategoryExpense(
                                    categoryName = categoryAmount.categoryName,
                                    categoryColor = categoryAmount.categoryColor?.toComposeColor(),
                                    amount = categoryAmount.totalAmount,
                                    percentage = categoryAmount.percentage
                                )
                            }

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
