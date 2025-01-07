package lmm.moneylog.ui.features.invoice.model

import lmm.moneylog.data.account.model.Account
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel

data class InvoiceListUIState(
    val titleResourceId: Int,
    val cardId: Int = -1,
    val invoiceCode: String = "",
    val transactions: List<TransactionModel> = emptyList(),
    val totalValue: String = "",
    val isInvoicePaid: Boolean = false,
    val cardName: String = "",
    val accounts: List<Account> = emptyList(),
    val isLoadingWhilePay: Boolean = false,
    val monthName: String = "",
)
