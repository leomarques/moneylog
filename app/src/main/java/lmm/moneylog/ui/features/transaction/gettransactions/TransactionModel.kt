package lmm.moneylog.ui.features.transaction.gettransactions

data class TransactionModel(
    val value: String,
    val isIncome: Boolean,
    val description: String,
    val account: String,
    val category: String,
    val date: String,
    val id: Int
)
