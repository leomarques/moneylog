package lmm.moneylog.ui.features.notification.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.notification.settings.model.CreditCardItem
import lmm.moneylog.ui.features.notification.settings.model.NotificationSettingsUIState

class NotificationSettingsViewModel(
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val notificationSettingsRepository: NotificationSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationSettingsUIState())
    val uiState: StateFlow<NotificationSettingsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCreditCardsRepository.getCreditCards().collect { creditCards ->
                val savedCreditCardId = notificationSettingsRepository.getDefaultCreditCardId()
                val creditCardItems =
                    creditCards.map {
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

    fun updatePermissions(
        hasListenerPermission: Boolean,
        hasBasicPermission: Boolean
    ) {
        _uiState.update {
            it.copy(
                hasListenerPermission = hasListenerPermission,
                hasBasicPermission = hasBasicPermission
            )
        }
    }

    fun selectCreditCard(creditCardItem: CreditCardItem?) {
        if (creditCardItem != null) {
            notificationSettingsRepository.saveDefaultCreditCardId(creditCardItem.id)
            _uiState.update {
                it.copy(selectedCreditCard = creditCardItem)
            }
        } else {
            notificationSettingsRepository.removeDefaultCreditCardId()
            _uiState.update {
                it.copy(selectedCreditCard = null)
            }
        }
    }
}
