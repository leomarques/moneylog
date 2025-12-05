package lmm.moneylog.graphs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.graphs.interactors.GetMonthlyTotalsInteractor
import lmm.moneylog.data.graphs.interactors.GetNetWorthHistoryInteractor
import lmm.moneylog.data.graphs.interactors.GetTransactionsByCategoryInteractor
import lmm.moneylog.data.graphs.model.CategoryAmount
import lmm.moneylog.data.graphs.model.MonthlyTotal
import lmm.moneylog.data.graphs.model.NetWorthPoint
import lmm.moneylog.data.time.repositories.DomainTimeRepository

private const val FIRST_MONTH = 1
private const val LAST_MONTH = 12

/**
 * ViewModel for the graphs screen
 * Handles data for displaying various graph types
 *
 * @property getTransactionsByCategoryInteractor Interactor to fetch transactions by category
 * @property getMonthlyTotalsInteractor Interactor to fetch monthly income/expense totals
 * @property getNetWorthHistoryInteractor Interactor to fetch net worth history
 * @property domainTimeRepository Repository for time-related operations
 */
class GraphsViewModel(
    private val getTransactionsByCategoryInteractor: GetTransactionsByCategoryInteractor,
    private val getMonthlyTotalsInteractor: GetMonthlyTotalsInteractor,
    private val getNetWorthHistoryInteractor: GetNetWorthHistoryInteractor,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _incomeByCategory = MutableStateFlow<List<CategoryAmount>>(emptyList())
    val incomeByCategory: StateFlow<List<CategoryAmount>> = _incomeByCategory.asStateFlow()

    private val _expensesByCategory = MutableStateFlow<List<CategoryAmount>>(emptyList())
    val expensesByCategory: StateFlow<List<CategoryAmount>> = _expensesByCategory.asStateFlow()

    private val _monthlyTotals = MutableStateFlow<List<MonthlyTotal>>(emptyList())
    val monthlyTotals: StateFlow<List<MonthlyTotal>> = _monthlyTotals.asStateFlow()

    private val _netWorthHistory = MutableStateFlow<List<NetWorthPoint>>(emptyList())
    val netWorthHistory: StateFlow<List<NetWorthPoint>> = _netWorthHistory.asStateFlow()

    private val _selectedMonth = MutableStateFlow(0)
    val selectedMonth: StateFlow<Int> = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(0)
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _monthName = MutableStateFlow("")
    val monthName: StateFlow<String> = _monthName.asStateFlow()

    private val _isIncome = MutableStateFlow(true)
    val isIncome: StateFlow<Boolean> = _isIncome.asStateFlow()

    init {
        viewModelScope.launch {
            val currentDate = domainTimeRepository.getCurrentDomainTime()
            _selectedMonth.value = currentDate.month
            _selectedYear.value = currentDate.year
            updateMonthName()
            loadData()
        }
    }

    private fun updateMonthName() {
        _monthName.value =
            domainTimeRepository
                .getMonthName(_selectedMonth.value)
                .replaceFirstChar { it.titlecase() }
    }

    private fun loadData() {
        viewModelScope.launch {
            // Load income data
            getTransactionsByCategoryInteractor
                .getIncomeByCategory(
                    month = _selectedMonth.value,
                    year = _selectedYear.value
                ).collect { data ->
                    _incomeByCategory.value = data
                }
        }

        viewModelScope.launch {
            // Load expenses data
            getTransactionsByCategoryInteractor
                .getExpensesByCategory(
                    month = _selectedMonth.value,
                    year = _selectedYear.value
                ).collect { data ->
                    _expensesByCategory.value = data
                }
        }

        viewModelScope.launch {
            // Load monthly totals
            getMonthlyTotalsInteractor
                .getMonthlyTotals(
                    currentMonth = _selectedMonth.value,
                    currentYear = _selectedYear.value
                ).collect { data ->
                    _monthlyTotals.value = data
                }
        }

        viewModelScope.launch {
            // Load net worth history
            getNetWorthHistoryInteractor
                .getNetWorthHistory(
                    currentMonth = _selectedMonth.value,
                    currentYear = _selectedYear.value
                ).collect { data ->
                    _netWorthHistory.value = data
                }
        }
    }

    /**
     * Navigate to the previous month
     */
    fun goToPreviousMonth() {
        if (_selectedMonth.value == FIRST_MONTH) {
            _selectedMonth.value = LAST_MONTH
            _selectedYear.value--
        } else {
            _selectedMonth.value--
        }
        updateMonthName()
        loadData()
    }

    /**
     * Navigate to the next month
     */
    fun goToNextMonth() {
        if (_selectedMonth.value == LAST_MONTH) {
            _selectedMonth.value = FIRST_MONTH
            _selectedYear.value++
        } else {
            _selectedMonth.value++
        }
        updateMonthName()
        loadData()
    }

    /**
     * Toggle between income and expense view
     */
    fun toggleIncomeExpense() {
        _isIncome.value = !_isIncome.value
    }

    /**
     * Set whether to show income or expenses
     */
    fun setIsIncome(value: Boolean) {
        _isIncome.value = value
    }
}
