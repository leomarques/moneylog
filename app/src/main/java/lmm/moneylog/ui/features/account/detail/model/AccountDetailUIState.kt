package lmm.moneylog.ui.features.account.detail.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.theme.neutralColor

data class AccountDetailUIState(
    val id: Int = -1,
    val name: String = "",
    val color: Color = neutralColor,
    val isEdit: Boolean = false,
    val showFab: Boolean = true
)
