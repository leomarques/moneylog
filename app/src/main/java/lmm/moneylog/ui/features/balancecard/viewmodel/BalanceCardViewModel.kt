package lmm.moneylog.ui.features.balancecard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.balance.GetBalanceInteractor
import lmm.moneylog.data.transaction.time.DomainTimeInteractor
import lmm.moneylog.ui.features.balancecard.model.BalanceCardUIState
import lmm.moneylog.ui.textformatters.formatForRs

class BalanceCardViewModel(
    interactor: GetBalanceInteractor,
    domainTimeInteractor: DomainTimeInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(BalanceCardUIState())
    val uiState: StateFlow<BalanceCardUIState> = _uiState.asStateFlow()

    init {
        val currentDomainTime = domainTimeInteractor.getCurrentDomainTime()

        viewModelScope.launch {
            interactor.execute(
                monthNumber = currentDomainTime.month,
                yearNumber = currentDomainTime.year
            ).collect {
                with(it) {
                    _uiState.value = BalanceCardUIState(
                        total = total.formatForRs(),
                        credit = credit.formatForRs(),
                        debt = debt.formatForRs(),
                        month = domainTimeInteractor.getCurrentMonthName()
                    )
                }
            }
        }
    }
}
