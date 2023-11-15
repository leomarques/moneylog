package lmm.moneylog.ui.features.transaction.list.model

data class TransactionsListUIState(
    val titleResourceId: Int,
    val transactions: List<TransactionModel> = emptyList()
)
