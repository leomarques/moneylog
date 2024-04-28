package lmm.moneylog.ui.features.credithomecard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.ui.features.creditcard.list.viewmodel.toCreditCardModelList
import lmm.moneylog.ui.features.credithomecard.model.CreditHomeCardUIState

class CreditHomeCardViewModel(repository: GetCreditCardsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditHomeCardUIState())
    val uiState: StateFlow<CreditHomeCardUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCreditCards().collect { creditCards ->
                _uiState.update {
                    CreditHomeCardUIState(
                        total = creditCards.size.toString(),
                        invoices =
                            creditCards
                                .toCreditCardModelList()
                                .map { it.name }
                    )
                }
            }
        }
    }
}
