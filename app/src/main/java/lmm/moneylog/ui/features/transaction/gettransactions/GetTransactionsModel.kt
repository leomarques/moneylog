package lmm.moneylog.ui.features.transaction.gettransactions

data class GetTransactionsModel(
    val transactions: List<TransactionModel>,
    val titleResourceId: Int
)
