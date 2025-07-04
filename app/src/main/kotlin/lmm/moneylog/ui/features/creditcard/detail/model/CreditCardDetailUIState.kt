package lmm.moneylog.ui.features.creditcard.detail.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.theme.neutralColor

data class CreditCardDetailUIState(
    val id: Int = -1,
    val name: String = "",
    val color: Color = neutralColor,
    val closingDay: String = "1",
    val dueDay: String = "7",
    val limit: String = "1000",
    val isEdit: Boolean = false,
    val showFab: Boolean = true
)
