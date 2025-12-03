package lmm.moneylog.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.balance.interactors.GetBalanceInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.home.models.BalanceInfo
import lmm.moneylog.ui.extensions.formatForRs

/**
 * ViewModel for the balance card component
 * Handles balance information display including total, change percentage, and change amount
 *
 * @property getBalanceInteractor Interactor to fetch balance information
 * @property domainTimeRepository Repository for time-related operations
 */
class BalanceCardViewModel(
    private val getBalanceInteractor: GetBalanceInteractor,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _balanceInfo = MutableStateFlow<BalanceInfo?>(null)
    val balanceInfo: StateFlow<BalanceInfo?> = _balanceInfo.asStateFlow()

    init {
        loadBalanceData()
    }

    private fun loadBalanceData() {
        viewModelScope.launch {
            val currentDomainTime = domainTimeRepository.getCurrentDomainTime()

            getBalanceInteractor
                .execute(
                    monthNumber = currentDomainTime.month,
                    yearNumber = currentDomainTime.year
                ).collect { balanceModel ->
                    _balanceInfo.value =
                        BalanceInfo(
                            balance = balanceModel.total.formatForRs(),
                            changePercentage = calculateChangePercentage(balanceModel.total),
                            changeAmount = calculateChangeAmount(balanceModel.total)
                        )
                }
        }
    }

    /**
     * Calculates the percentage change for the balance
     * TODO: Implement proper calculation with previous period data
     */
    private fun calculateChangePercentage(currentBalance: Double): String {
        // Placeholder: This should compare with previous period
        return "+8.2%"
    }

    /**
     * Calculates the absolute change amount for the balance
     * TODO: Implement proper calculation with previous period data
     */
    private fun calculateChangeAmount(currentBalance: Double): String {
        // Placeholder: This should compare with previous period
        return "+${kotlin.math.abs(currentBalance * 0.082).formatForRs()}"
    }
}
