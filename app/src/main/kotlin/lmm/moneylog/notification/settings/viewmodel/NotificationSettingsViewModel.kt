package lmm.moneylog.notification.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.notification.predictor.CreditCardPredictor
import lmm.moneylog.notification.settings.model.CreditCardItem
import lmm.moneylog.notification.settings.model.NotificationSettingsUIState
import lmm.moneylog.ui.extensions.toComposeColor

class NotificationSettingsViewModel(
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val creditCardPredictor: CreditCardPredictor
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationSettingsUIState())
    val uiState: StateFlow<NotificationSettingsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCreditCardsRepository.getCreditCards().collect { creditCards ->
                val savedCreditCardId = creditCardPredictor.getCreditCardId()
                val creditCardItems = creditCards.map {
                    CreditCardItem(
                        id = it.id,
                        name = it.name,
                        color = it.color.toComposeColor()
                    )
                }
                val selectedCard = creditCardItems.firstOrNull { it.id == savedCreditCardId }

                _uiState.update { currentState ->
                    currentState.copy(
                        creditCards = creditCardItems,
                        selectedCreditCard = selectedCard
                    )
                }
            }
        }
    }

    fun updatePermissions(hasListenerPermission: Boolean, hasBasicPermission: Boolean) {
        _uiState.update {
            it.copy(
                hasListenerPermission = hasListenerPermission,
                hasBasicPermission = hasBasicPermission
            )
        }
    }

    fun selectCreditCard(creditCardItem: CreditCardItem?) {
        if (creditCardItem != null) {
            creditCardPredictor.saveCreditCardId(creditCardItem.id)
            _uiState.update {
                it.copy(selectedCreditCard = creditCardItem)
            }
        } else {
            creditCardPredictor.removeCreditCardId()
            _uiState.update {
                it.copy(selectedCreditCard = null)
            }
        }
    }
}
