package lmm.moneylog.ui.features.transactiondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.gettransaction.GetTransactionInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter

class TransactionDetailViewModel(
    getTransactionInteractor: GetTransactionInteractor,
    private val addTransactionInteractor: AddTransactionInteractor,
    private val updateTransactionInteractor: UpdateTransactionInteractor,
    private val domainTimeConverter: DomainTimeConverter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val transactionDetailModel: LiveData<TransactionDetailModel>

    init {
        val id = savedStateHandle["id"] ?: -1

        transactionDetailModel = if (id != -1) {
            getTransactionInteractor.getTransaction(id).asLiveData().map { transaction ->
                TransactionDetailModel(
                    value = mutableStateOf(
                        if (transaction.value > 0) {
                            "${transaction.value}"
                        } else {
                            "${-transaction.value}"
                        }
                    ),
                    isIncome = mutableStateOf(transaction.value > 0),
                    displayDate = mutableStateOf(convertToDisplayDate(transaction.date)),
                    description = mutableStateOf(transaction.description),
                    date = transaction.date,
                    isEdit = true,
                    id = id,
                    titleResourceId = R.string.detailtransaction_topbar_title_edit
                )
            }
        } else {
            MutableLiveData(
                TransactionDetailModel(
                    value = mutableStateOf(""),
                    isIncome = mutableStateOf(true),
                    displayDate = mutableStateOf(
                        convertToDisplayDate(
                            domainTimeConverter.timeStampToDomainTime(
                                domainTimeConverter.getCurrentTimeStamp()
                            )
                        )
                    ),
                    description = mutableStateOf(""),
                    date = domainTimeConverter.timeStampToDomainTime(
                        domainTimeConverter.getCurrentTimeStamp()
                    ),
                    isEdit = false,
                    id = id,
                    titleResourceId = R.string.detailtransaction_topbar_title_add
                )
            )
        }
    }

    fun onTypeOfValueSelected(isIncome: Boolean) {
        transactionDetailModel.value?.isIncome?.value = isIncome
    }

    fun onDatePicked(timeStamp: Long) {
        with(transactionDetailModel.value!!) {
            date = domainTimeConverter.timeStampToDomainTime(timeStamp)
            displayDate.value = convertToDisplayDate(date)
        }
    }

    private fun convertToDisplayDate(domainTime: DomainTime): String {
        return "${domainTime.day} ${domainTimeConverter.getMonthName(domainTime.month)}, ${domainTime.year}"
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

    fun onFabClick(transactionModel: TransactionDetailModel, onSuccess: () -> Unit, onError: () -> Unit) {
        try {
            if (transactionModel.isEdit) {
                updateTransaction(transactionModel, onSuccess)
            } else {
                saveTransaction(transactionModel, onSuccess)
            }
        } catch (e: NumberFormatException) {
            onError()
        }
    }

    private fun updateTransaction(
        transactionDetailModel: TransactionDetailModel,
        onSuccess: () -> Unit
    ) {
        with(transactionDetailModel) {
            val transaction = Transaction(
                value = validateValue(value.value, isIncome.value),
                date = date,
                description = description.value,
                id = id
            )

            viewModelScope.launch {
                updateTransactionInteractor.execute(transaction)
                onSuccess()
            }
        }
    }

    private fun saveTransaction(
        transactionDetailModel: TransactionDetailModel,
        onSuccess: () -> Unit
    ) {
        with(transactionDetailModel) {
            val transaction = Transaction(
                value = validateValue(value.value, isIncome.value),
                date = date,
                description = description.value
            )

            viewModelScope.launch {
                addTransactionInteractor.execute(transaction)
                onSuccess()
            }
        }
    }
}
