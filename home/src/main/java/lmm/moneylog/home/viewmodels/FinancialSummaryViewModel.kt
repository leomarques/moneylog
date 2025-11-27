package lmm.moneylog.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.balance.interactors.GetBalanceInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.home.models.FinancialSummary
import lmm.moneylog.home.utils.formatToBRL

/**
 * ViewModel for the financial summary cards (Income and Expenses)
 * Handles income and expense data display
 *
 * @property getBalanceInteractor Interactor to fetch balance information
 * @property domainTimeRepository Repository for time-related operations
 */
class FinancialSummaryViewModel(
    private val getBalanceInteractor: GetBalanceInteractor,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _income = MutableStateFlow<FinancialSummary?>(null)
    val income: StateFlow<FinancialSummary?> = _income.asStateFlow()

    private val _expenses = MutableStateFlow<FinancialSummary?>(null)
    val expenses: StateFlow<FinancialSummary?> = _expenses.asStateFlow()

    init {
        loadFinancialData()
    }

    private fun loadFinancialData() {
        viewModelScope.launch {
            val currentDomainTime = domainTimeRepository.getCurrentDomainTime()

            getBalanceInteractor
                .execute(
                    monthNumber = currentDomainTime.month,
                    yearNumber = currentDomainTime.year
                ).collect { balanceModel ->
                    _income.value =
                        FinancialSummary(
                            amount = balanceModel.credit.formatToBRL()
                        )

                    _expenses.value =
                        FinancialSummary(
                            amount = balanceModel.debt.formatToBRL()
                        )
                }
        }
    }
}
