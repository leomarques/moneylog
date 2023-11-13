package lmm.moneylog.ui.extensions

import androidx.compose.ui.graphics.Color
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.model.Account
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.data.transaction.time.DomainTimeInteractor
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState

fun TransactionDetailUIState.toTransaction(): Transaction = Transaction(
    value = value.validateValue(isIncome),
    date = date,
    description = description,
    id = id,
    accountId = accountId,
    categoryId = categoryId
)

fun Transaction.toDetailModel(domainTimeInteractor: DomainTimeInteractor) =
    TransactionDetailUIState(
        id = id,
        value = value.toPositiveString(),
        isIncome = value > 0,
        description = description,
        date = date,
        displayDate = date.convertToDisplayDate(domainTimeInteractor),
        isEdit = true,
        titleResourceId = R.string.detail_topbar_transaction_edit,
        accountId = accountId,
        categoryId = categoryId
    )

private fun Double.toPositiveString(): String {
    val positive = if (this < 0) -this else this
    return if (positive.isWhole()) positive.toString().removeDecimal() else "$positive"
}

private fun Double.isWhole() = this % 1.0 == 0.0

private fun String.removeDecimal() = substring(0, length - 2)

fun DomainTime.convertToDisplayDate(domainTimeInteractor: DomainTimeInteractor) =
    "$day ${domainTimeInteractor.getMonthName(month)}, $year"

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
