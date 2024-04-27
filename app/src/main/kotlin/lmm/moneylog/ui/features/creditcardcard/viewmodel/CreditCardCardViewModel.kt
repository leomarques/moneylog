package lmm.moneylog.ui.features.creditcardcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.ui.features.creditcardcard.model.CreditCardCardUIState

class CreditCardCardViewModel(
//    interactor: GetCreditCardInteractor,
//    domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditCardCardUIState())
    val uiState: StateFlow<CreditCardCardUIState> = _uiState.asStateFlow()

    init {
//        val currentDomainTime = domainTimeRepository.getCurrentDomainTime()

        viewModelScope.launch {
//            interactor.execute(
//                monthNumber = currentDomainTime.month,
//                yearNumber = currentDomainTime.year
//            ).collect {
//                with(it) {
//                    _uiState.value =
//                        CreditCardCardUIState(
//                            total = total.formatForRs(),
//                            credit = credit.formatForRs(),
//                            debt = debt.formatForRs(),
//                            month = domainTimeRepository.getCurrentMonthName()
//                        )
//                }
//            }
        }
    }
}
