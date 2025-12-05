package lmm.moneylog.home.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.interactors.GetCreditCardHomeInfoInteractor
import lmm.moneylog.home.models.CreditCardInfo
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.extensions.toComposeColor

/**
 * ViewModel for the credit cards section
 * Handles credit card data display with invoice information
 *
 * @property getCreditCardHomeInfoInteractor Interactor to fetch credit card data
 */
class CreditCardsViewModel(
    private val getCreditCardHomeInfoInteractor: GetCreditCardHomeInfoInteractor
) : ViewModel() {
    private val _creditCards = MutableStateFlow<List<CreditCardInfo>>(emptyList())
    val creditCards: StateFlow<List<CreditCardInfo>> = _creditCards.asStateFlow()

    init {
        loadCreditCards()
    }

    private fun loadCreditCards() {
        viewModelScope.launch {
            getCreditCardHomeInfoInteractor.execute().collect { creditCardResult ->
                _creditCards.value =
                    creditCardResult.cards.map { card ->
                        CreditCardInfo(
                            cardId = card.id,
                            cardName = card.name,
                            invoiceAmount = card.value.formatForRs(),
                            invoiceCode = creditCardResult.invoiceCode,
                            cardColor = card.color.toComposeColor()
                        )
                    }
            }
        }
    }

}
