package lmm.moneylog.ui.features.transaction.detail.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.R
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.ui.theme.neutralColor

data class TransactionDetailUIState(
    val id: Int = -1,
    val titleResourceId: Int = R.string.transaction_topbar_add,
    val isEdit: Boolean = false,
    val showFab: Boolean = true,
    val isIncome: Boolean = true,
    val isDebtSelected: Boolean = true,
    val value: String = "",
    val description: String = "",
    val displayDate: String = "",
    val displayAccount: String = "",
    val displayCategory: String = "",
    val displayCreditCard: String = "",
    val displayInvoice: String = "",
    val displayAccountColor: Color = neutralColor,
    val displayCategoryColor: Color = neutralColor,
    val displayCreditCardColor: Color = neutralColor,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val creditCardId: Int? = null,
    val invoiceCode: String? = null,
    val date: DomainTime = DomainTime(),
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val creditCards: List<CreditCard> = emptyList(),
    val invoices: List<Invoice> = emptyList(),
    val descriptionSuggestions: List<TransactionSuggestion> = emptyList()
)
