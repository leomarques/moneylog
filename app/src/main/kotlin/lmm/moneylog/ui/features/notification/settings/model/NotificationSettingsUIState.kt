package lmm.moneylog.ui.features.notification.settings.model

import androidx.compose.ui.graphics.Color

data class NotificationSettingsUIState(
    val hasListenerPermission: Boolean = false,
    val hasBasicPermission: Boolean = false,
    val creditCards: List<CreditCardItem> = emptyList(),
    val selectedCreditCard: CreditCardItem? = null
)

data class CreditCardItem(
    val id: Int,
    val name: String,
    val color: Color
)
