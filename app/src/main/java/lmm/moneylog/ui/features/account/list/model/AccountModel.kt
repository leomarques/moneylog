package lmm.moneylog.ui.features.account.list.model

import androidx.compose.ui.graphics.Color

data class AccountModel(
    val id: Int,
    val name: String,
    val balance: String,
    val color: Color
)
