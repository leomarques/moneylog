package lmm.moneylog.ui.features.account.getaccounts

import androidx.compose.ui.graphics.Color

data class AccountModel(
    val id: Int,
    val name: String,
    val balance: Double,
    val color: Color
)
