package lmm.moneylog.ui.features.creditcard.homecard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.interactors.GetCreditCardHomeInfoInteractor
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditCardHomeCardItem
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditHomeCardUIState

class CreditHomeCardViewModel(
    private val getCreditCardHomeInfoInteractor: GetCreditCardHomeInfoInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditHomeCardUIState())
    val uiState: StateFlow<CreditHomeCardUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCreditCardHomeInfoInteractor.execute().collect { result ->
                _uiState.update {
                    CreditHomeCardUIState(
                        cards = result.cards.map { card ->
                            CreditCardHomeCardItem(
                                id = card.id,
                                name = card.name,
                                value = card.value,
                                color = card.color
                            )
                        },
                        invoiceCode = result.invoiceCode
                    )
                }
            }
        }
    }
}
