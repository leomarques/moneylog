package lmm.moneylog.ui.features.creditcard.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.creditcard.list.model.CreditCardModel
import lmm.moneylog.ui.features.creditcard.list.model.CreditCardsListUIState

class CreditCardsListViewModel(
    private val getCreditCardsRepository: GetCreditCardsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditCardsListUIState())
    val uiState: StateFlow<CreditCardsListUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCreditCardsRepository.getCreditCards().collect { creditCards ->
                _uiState.update {
                    CreditCardsListUIState(
                        creditCards
                            .toCreditCardModelList()
                            .reversed()
                    )
                }
            }
        }
    }
}

fun List<CreditCard>.toCreditCardModelList(): List<CreditCardModel> =
    this.map {
        CreditCardModel(
            id = it.id,
            name = it.name,
            color = it.color.toComposeColor()
        )
    }
