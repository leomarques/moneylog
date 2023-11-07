package lmm.moneylog.ui.features.transaction.list.model

data class GetTransactionsModel(
    val transactions: List<TransactionModel> = emptyList(),
    val titleResourceId: Int
)
