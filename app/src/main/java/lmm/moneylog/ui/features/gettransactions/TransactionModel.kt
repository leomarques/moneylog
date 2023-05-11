package lmm.moneylog.ui.features.gettransactions

data class TransactionModel(
    val value: String,
    val isIncome: Boolean,
    val description: String,
    val date: String
)
