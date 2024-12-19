package lmm.moneylog.ui.features.invoice.model

import lmm.moneylog.ui.features.transaction.list.model.TransactionModel

data class InvoiceListUIState(
    val titleResourceId: Int,
    val cardId: Int = -1,
    val invoiceCode: String = "",
    val transactions: List<TransactionModel> = emptyList(),
    val totalValue: String = "",
    val isInvoicePaid: Boolean = false,
    val cardName: String = ""
)
