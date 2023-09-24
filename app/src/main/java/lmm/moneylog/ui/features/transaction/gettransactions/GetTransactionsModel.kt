package lmm.moneylog.ui.features.transaction.gettransactions

data class GetTransactionsModel(
    val transactions: List<TransactionModel> = emptyList(),
    val titleResourceId: Int
)
