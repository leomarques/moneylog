package lmm.moneylog.ui.features.gettransactions

import lmm.moneylog.data.gettransactions.TransactionModel

data class GetTransactionsModel(
    val transactions: List<TransactionModel>,
    val titleResourceId: Int
)
