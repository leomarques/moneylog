package lmm.moneylog.ui.features.transaction.list.model

data class TransactionsListUIState(
    val titleResourceId: Int,
    val monthName: String = "",
    val transactions: List<TransactionModel> = emptyList(),
    val total: Double = 0.0
)
