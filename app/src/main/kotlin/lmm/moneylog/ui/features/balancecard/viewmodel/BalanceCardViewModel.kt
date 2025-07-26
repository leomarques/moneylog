package lmm.moneylog.ui.features.balancecard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.balance.interactors.GetBalanceInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.balancecard.model.BalanceCardUIState

class BalanceCardViewModel(
    interactor: GetBalanceInteractor,
    domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BalanceCardUIState())
    val uiState: StateFlow<BalanceCardUIState> = _uiState.asStateFlow()

    init {
        val currentDomainTime = domainTimeRepository.getCurrentDomainTime()

        viewModelScope.launch {
            interactor
                .execute(
                    monthNumber = currentDomainTime.month,
                    yearNumber = currentDomainTime.year
                ).collect {
                    with(it) {
                        _uiState.value =
                            BalanceCardUIState(
                                total = total.formatForRs(),
                                credit = credit.formatForRs(),
                                debt = debt.formatForRs(),
                                month = domainTimeRepository.getCurrentMonthName()
                            )
                    }
                }
        }
    }
}
