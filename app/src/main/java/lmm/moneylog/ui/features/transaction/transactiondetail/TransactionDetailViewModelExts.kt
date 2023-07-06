package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import lmm.moneylog.R
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter
import lmm.moneylog.domain.transaction.Transaction

fun DomainTime.convertToDisplayDate(domainTimeConverter: DomainTimeConverter) =
    "$day ${domainTimeConverter.getMonthName(month)}, $year"

fun String.validateValue(isIncome: Boolean): Double {
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

fun Transaction.toDetailModel(domainTimeConverter: DomainTimeConverter) =
    TransactionDetailModel(
        value = mutableStateOf(
            if (value > 0) {
                "$value"
            } else {
                "${-value}"
            }
        ),
        isIncome = mutableStateOf(value > 0),
        displayDate = mutableStateOf(
            date.convertToDisplayDate(
                domainTimeConverter
            )
        ),
        description = mutableStateOf(description),
        date = date,
        isEdit = true,
        id = id,
        titleResourceId = R.string.detailtransaction_topbar_title_edit
    )

fun TransactionDetailModel.updateTime(timeStamp: Long, domainTimeConverter: DomainTimeConverter) {
    date = domainTimeConverter.timeStampToDomainTime(timeStamp)
    displayDate.value = date.convertToDisplayDate(domainTimeConverter)
}

fun TransactionDetailModel.toTransaction(): Transaction = Transaction(
    value = value.value.validateValue(isIncome.value),
    date = date,
    description = description.value,
    id = id
)

fun SavedStateHandle.getIdParam(): Int? {
    get<Int>("id")?.let { id ->
        return if (id != -1) id else null
    } ?: return null
}
