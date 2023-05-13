package lmm.moneylog.ui.features.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter

class AddTransactionViewModel(
    private val interactor: AddTransactionInteractor,
    private val domainTimeConverter: DomainTimeConverter,
) : ViewModel() {

    val addTransactionModel = AddTransactionModel()

    init {
        onDatePicked(domainTimeConverter.getCurrentTimeStamp())
    }

    fun onDatePicked(timeStamp: Long) {
        with(addTransactionModel) {
            date = domainTimeConverter.timeStampToDomainTime(timeStamp)
            displayDate.value = convertToDisplayDate(date)
        }
    }

    private fun convertToDisplayDate(domainTime: DomainTime): String {
        return "${domainTime.day} ${domainTimeConverter.getMonthName(domainTime.month)}, ${domainTime.year}"
    }

    fun saveTransaction(
        addTransactionModel: AddTransactionModel,
        onValueError: () -> Unit
    ) {
        try {
            with(addTransactionModel) {
                val transaction = Transaction(
                    value = validateValue(value.value, isIncome),
                    date = date,
                    description = description.value
                )

                viewModelScope.launch {
                    interactor.execute(transaction)
                }
            }
        } catch (e: NumberFormatException) {
            onValueError()
        }
    }

    private fun validateValue(valueParam: String, isIncome: Boolean): Double {
        val value = valueParam.toDouble()
        if (value <= 0.0) {
            throw java.lang.NumberFormatException("Negative number")
        }

        return if (isIncome) {
            value
        } else {
            -value
        }
    }

    fun onTypeOfValueSelected(isIncome: Boolean) {
        addTransactionModel.isIncome = isIncome
    }
}
