package lmm.moneylog.data.gettransactions

data class TransactionModel(
    val value: String,
    val isIncome: Boolean,
    val description: String,
    val date: String,
    val id: Int
)
