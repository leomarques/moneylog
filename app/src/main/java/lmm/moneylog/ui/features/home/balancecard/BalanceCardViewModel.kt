package lmm.moneylog.ui.features.home.balancecard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.balance.GetBalanceInteractor
import lmm.moneylog.data.transaction.time.DomainTimeInteractor
import lmm.moneylog.ui.textformatters.formatForRs

class BalanceCardViewModel(
    interactor: GetBalanceInteractor,
    domainTimeInteractor: DomainTimeInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        BalanceCardUIState(
            total = "",
            credit = "",
            debt = "",
            month = ""
        )
    )
    val uiState: StateFlow<BalanceCardUIState> = _uiState.asStateFlow()

    init {
        val currentDomainTime = domainTimeInteractor.getCurrentDomainTime()
        viewModelScope.launch {
            interactor.execute(
                monthNumber = currentDomainTime.month,
                yearNumber = currentDomainTime.year
            ).collect { balanceModel ->
                _uiState.value = BalanceCardUIState(
                    total = balanceModel.total.formatForRs(),
                    credit = balanceModel.credit.formatForRs(),
                    debt = balanceModel.debt.formatForRs(),
                    month = domainTimeInteractor.getCurrentMonthName()
                )
            }
        }
    }
}
