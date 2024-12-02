package lmm.moneylog.ui.features.transaction.detail.viewmodel

import androidx.compose.ui.graphics.Color
import lmm.moneylog.R
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.misc.toDisplayInvoice
import lmm.moneylog.data.misc.toInvoiceMonth
import lmm.moneylog.data.misc.toInvoiceYear
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.extensions.validateValue
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState

fun TransactionDetailUIState.toTransaction(): Transaction =
    Transaction(
        value = value.validateValue(isIncome),
        date = date,
        description = description,
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        creditCardId = creditCardId,
        invoiceMonth = invoiceCode.toInvoiceMonth(),
        invoiceYear = invoiceCode.toInvoiceYear()
    )

fun Transaction.toDetailModel(domainTimeRepository: DomainTimeRepository) =
    TransactionDetailUIState(
        id = id,
        titleResourceId = R.string.detail_topbar_transaction_edit,
        isEdit = true,
        isIncome = value > 0,
        value = value.toPositiveString(),
        description = description,
        displayDate = date.convertToDisplayDate(domainTimeRepository),
        accountId = accountId,
        categoryId = categoryId,
        creditCardId = creditCardId,
        invoiceCode = "$invoiceMonth.$invoiceYear",
        displayInvoice = "$invoiceMonth.$invoiceYear".toDisplayInvoice(domainTimeRepository),
        date = date,
        isDebtSelected = creditCardId == null
    )

private fun Double.toPositiveString(): String {
    val positive = if (this < 0) -this else this
    return if (positive.isWhole()) positive.toString().removeDecimal() else "$positive"
}

private fun Double.isWhole() = this % 1.0 == 0.0

private fun String.removeDecimal() = substring(0, length - 2)

fun DomainTime.convertToDisplayDate(domainTimeRepository: DomainTimeRepository): String {
    return "$day ${domainTimeRepository.getMonthName(month)}, $year"
}

fun List<Account>.getAccountById(accountId: Int?): (Pair<String, Color>)? {
    return firstOrNull {
        it.id == accountId
    }?.let {
        it.name to it.color.toComposeColor()
    }
}

fun List<Category>.getCategoryById(categoryId: Int?): (Pair<String, Color>)? {
    return firstOrNull {
        it.id == categoryId
    }?.let {
        it.name to it.color.toComposeColor()
    }
}

fun List<CreditCard>.getCreditCardById(creditCardId: Int?): (Pair<String, Color>)? {
    return firstOrNull {
        it.id == creditCardId
    }?.let {
        it.name to it.color.toComposeColor()
    }
}
