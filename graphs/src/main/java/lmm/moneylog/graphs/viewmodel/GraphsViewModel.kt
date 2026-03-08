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

    // Pie Chart month (user selectable)
    private val _selectedMonth = MutableStateFlow(0)
    val selectedMonth: StateFlow<Int> = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(0)
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _monthName = MutableStateFlow("")
    val monthName: StateFlow<String> = _monthName.asStateFlow()

    private val _isIncome = MutableStateFlow(false)
    val isIncome: StateFlow<Boolean> = _isIncome.asStateFlow()

    private val _monthlyTotal = MutableStateFlow(0.0)
    val monthlyTotal: StateFlow<Double> = _monthlyTotal.asStateFlow()

    init {
        viewModelScope.launch {
            val currentDate = domainTimeRepository.getCurrentDomainTime()
            _selectedMonth.value = currentDate.month
            _selectedYear.value = currentDate.year
            updateMonthName()
            loadAllData()
        }
    }

    private fun updateMonthName() {
        _monthName.value =
            domainTimeRepository
                .getMonthName(_selectedMonth.value)
                .replaceFirstChar { it.titlecase() }
    }

    /**
     * Load all data - Pie Chart uses selected month, Bar/Net Worth use current date
     */
    private fun loadAllData() {
        loadPieChartData()
        loadBarChartAndNetWorthData()
    }

    /**
     * Load data for Pie Chart tab (uses user-selected month)
     */
    private fun loadPieChartData() {
        viewModelScope.launch {
            // Load income data
            getTransactionsByCategoryInteractor
                .getIncomeByCategory(
                    month = _selectedMonth.value,
                    year = _selectedYear.value
                ).collect { data ->
                    _incomeByCategory.value = data
                    updateMonthlyTotal()
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
                    updateMonthlyTotal()
                }
        }
    }

    /**
     * Load data for Bar Chart and Net Worth tabs (always uses current date)
     */
    private fun loadBarChartAndNetWorthData() {
        val currentDate = domainTimeRepository.getCurrentDomainTime()

        viewModelScope.launch {
            // Load monthly totals (always up to current date)
            getMonthlyTotalsInteractor
                .getMonthlyTotals(
                    currentMonth = currentDate.month,
                    currentYear = currentDate.year
                ).collect { data ->
                    _monthlyTotals.value = data
                }
        }

        viewModelScope.launch {
            // Load net worth history (always up to current date)
            getNetWorthHistoryInteractor
                .getNetWorthHistory(
                    currentMonth = currentDate.month,
                    currentYear = currentDate.year
                ).collect { data ->
                    _netWorthHistory.value = data
                }
        }
    }

    private fun updateMonthlyTotal() {
        val totalIncome = _incomeByCategory.value.sumOf { it.totalAmount }
        val totalExpenses = _expensesByCategory.value.sumOf { it.totalAmount }
        _monthlyTotal.value = totalIncome - totalExpenses
    }

    /**
     * Navigate to the previous month (Pie Chart only)
     */
    fun goToPreviousMonth() {
        if (_selectedMonth.value == FIRST_MONTH) {
            _selectedMonth.value = LAST_MONTH
            _selectedYear.value--
        } else {
            _selectedMonth.value--
        }
        updateMonthName()
        loadPieChartData()
    }

    /**
     * Navigate to the next month (Pie Chart only)
     */
    fun goToNextMonth() {
        if (_selectedMonth.value == LAST_MONTH) {
            _selectedMonth.value = FIRST_MONTH
            _selectedYear.value++
        } else {
            _selectedMonth.value++
        }
        updateMonthName()
        loadPieChartData()
    }

    /**
     * Toggle between income and expense view
     */
    fun toggleIncomeExpense() {
        _isIncome.value = !_isIncome.value
    }
}
