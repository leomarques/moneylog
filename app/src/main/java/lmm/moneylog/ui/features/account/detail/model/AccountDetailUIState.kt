package lmm.moneylog.ui.features.account.detail.model

import androidx.compose.ui.graphics.Color

data class AccountDetailUIState(
    val id: Int = -1,
    val name: String = "",
    val color: Color = Color.Gray,
    val isEdit: Boolean = false,
    val showFab: Boolean = true
)
