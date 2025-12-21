package lmm.moneylog.ui.features.transaction.list.model

import androidx.compose.ui.graphics.Color

data class TransactionModel(
    val value: String,
    val isIncome: Boolean,
    val description: String,
    val account: String,
    val category: String,
    val date: String,
    val id: Long,
    val categoryColor: Color,
    val creditCard: String
)
