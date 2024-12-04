package lmm.moneylog.ui.features.invoice

import lmm.moneylog.ui.features.transaction.list.model.TransactionModel

data class InvoiceListUIState(
    val titleResourceId: Int,
    val transactions: List<TransactionModel> = emptyList()
)
