package lmm.moneylog.ui.features.transaction.transactiondetail

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
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter
import lmm.moneylog.domain.transaction.Transaction
import lmm.moneylog.domain.transaction.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.transaction.deletetransaction.DeleteTransactionInteractor
import lmm.moneylog.domain.transaction.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.transaction.gettransaction.GetTransactionInteractor

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getTransactionInteractor: GetTransactionInteractor,
    private val addTransactionInteractor: AddTransactionInteractor,
    private val updateTransactionInteractor: UpdateTransactionInteractor,
    private val deleteTransactionInteractor: DeleteTransactionInteractor,
    private val domainTimeConverter: DomainTimeConverter
) : ViewModel() {

    val transactionDetailModel: LiveData<TransactionDetailModel>

    init {
        val id = savedStateHandle["id"] ?: -1

        transactionDetailModel = if (id != -1) {
            getTransactionInteractor.getTransaction(id).asLiveData().map { transaction ->
                if (transaction != null) {
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
                } else {
                    provideDefaultModel()
                }
            }
        } else {
            MutableLiveData(
                provideDefaultModel()
            )
        }
    }

    private fun provideDefaultModel() =
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
            id = -1,
            titleResourceId = R.string.detailtransaction_topbar_title_add
        )

    fun onTypeOfValueSelected(isIncome: Boolean) {
        transactionDetailModel.value?.isIncome?.value = isIncome
    }

    fun onDatePicked(timeStamp: Long) {
        transactionDetailModel.value?.let {
            it.date = domainTimeConverter.timeStampToDomainTime(timeStamp)
            it.displayDate.value = convertToDisplayDate(it.date)
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

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            if (transactionDetailModel.value?.isEdit == true) {
                updateTransaction()
            } else {
                saveTransaction()
            }
            onSuccess()
        } catch (e: NumberFormatException) {
            onError()
        }
    }

    private fun updateTransaction() {
        transactionDetailModel.value?.let {
            val transaction = Transaction(
                value = validateValue(it.value.value, it.isIncome.value),
                date = it.date,
                description = it.description.value,
                id = it.id
            )

            viewModelScope.launch {
                updateTransactionInteractor.execute(transaction)
            }
        }
    }

    private fun saveTransaction() {
        transactionDetailModel.value?.let {
            val transaction = Transaction(
                value = validateValue(it.value.value, it.isIncome.value),
                date = it.date,
                description = it.description.value
            )

            viewModelScope.launch {
                addTransactionInteractor.execute(transaction)
            }
        }
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionInteractor.execute(id)
        }
    }
}
