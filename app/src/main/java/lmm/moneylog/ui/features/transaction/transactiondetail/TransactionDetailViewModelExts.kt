package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import lmm.moneylog.R
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.data.transaction.time.DomainTimeInteractor

fun DomainTime.convertToDisplayDate(domainTimeInteractor: DomainTimeInteractor) =
    "$day ${domainTimeInteractor.getMonthName(month)}, $year"

fun String.validateValue(isIncome: Boolean = true): Double {
    val value = toDouble()
    if (value <= 0.0) {
        throw java.lang.NumberFormatException("Negative number")
    }

    return if (isIncome) {
        value
    } else {
        -value
    }
}

fun Transaction.toDetailModel(domainTimeInteractor: DomainTimeInteractor) =
    TransactionDetailModel(
        id = id,
        value = mutableStateOf(value.toPositiveString()),
        isIncome = mutableStateOf(value > 0),
        description = mutableStateOf(description),
        date = date,
        displayDate = date.convertToDisplayDate(domainTimeInteractor),
        isEdit = true,
        titleResourceId = R.string.detailtransaction_topbar_title_edit,
        accountId = accountId,
        categoryId = categoryId
    )

private fun Double.toPositiveString(): String {
    val positive = if (this < 0) -this else this
    return if (positive.isWhole()) positive.toString().removeDecimal() else "$positive"
}

private fun Double.isWhole() = this % 1.0 == 0.0

private fun String.removeDecimal() = substring(0, length - 2)

fun TransactionDetailModel.toTransaction(): Transaction = Transaction(
    value = value.value.validateValue(isIncome.value),
    date = date,
    description = description.value,
    id = id,
    accountId = accountId,
    categoryId = categoryId
)

fun SavedStateHandle.getIdParam(): Int? {
    get<Int>("id")?.let { id ->
        return if (id != -1) id else null
    } ?: return null
}
