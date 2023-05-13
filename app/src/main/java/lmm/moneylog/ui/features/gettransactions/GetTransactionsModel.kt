package lmm.moneylog.ui.features.gettransactions

data class GetTransactionsModel(
    val transactions: List<TransactionModel>,
    val titleResourceId: Int
)
