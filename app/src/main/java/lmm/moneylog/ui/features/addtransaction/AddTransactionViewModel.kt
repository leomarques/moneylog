package lmm.moneylog.ui.features.addtransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.model.Transaction
import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.addtransaction.time.DomainTimeConverter

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

    fun saveTransaction(addTransactionModel: AddTransactionModel) {
        try {
            with(addTransactionModel) {
                val transaction = Transaction(
                    value =
                    if (addTransactionModel.isIncome)
                        value.value.toDouble()
                    else
                        -value.value.toDouble(),
                    date = date,
                    description = description.value
                )

                viewModelScope.launch {
                    interactor.execute(transaction)
                }
            }
        } catch (e: NumberFormatException) {
            Log.e(
                /* tag = */ "saveTransaction",
                /* msg = */ "NumberFormatException: " + e.localizedMessage
            )
        }
    }

    fun onTypeOfIncomeSelected(isIncomeParam: Boolean) {
        addTransactionModel.isIncome = isIncomeParam
    }
}
