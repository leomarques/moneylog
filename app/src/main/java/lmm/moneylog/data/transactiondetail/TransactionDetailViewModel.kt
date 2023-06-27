package lmm.moneylog.data.transactiondetail

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
import lmm.moneylog.domain.deletetransaction.DeleteTransactionInteractor
import lmm.moneylog.domain.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.gettransaction.GetTransactionInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter

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
                        id = id,
                        value = mutableStateOf(
                            if (transaction.value > 0) {
                                "${transaction.value}"
                            } else {
                                "${-transaction.value}"
                            }
                        ),
                        isIncome = mutableStateOf(transaction.value > 0),
                        date = transaction.date,
                        displayDate = mutableStateOf(convertToDisplayDate(transaction.date)),
                        description = mutableStateOf(transaction.description),
                        isEdit = true,
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

    fun provideDefaultModel() =
        TransactionDetailModel(
            id = -1,
            value = mutableStateOf(""),
            isIncome = mutableStateOf(true),
            date = domainTimeConverter.timeStampToDomainTime(
                domainTimeConverter.getCurrentTimeStamp()
            ),
            displayDate = mutableStateOf(
                convertToDisplayDate(
                    domainTimeConverter.timeStampToDomainTime(
                        domainTimeConverter.getCurrentTimeStamp()
                    )
                )
            ),
            description = mutableStateOf(""),
            isEdit = false,
            titleResourceId = R.string.detailtransaction_topbar_title_add
        )

    fun onTypeOfValueSelected(isIncome: Boolean) {
        transactionDetailModel.value?.isIncome?.value = isIncome
    }

    fun onDatePicked(timeStamp: Long) =
        transactionDetailModel.value?.let {
            it.date = domainTimeConverter.timeStampToDomainTime(timeStamp)
            it.displayDate.value = convertToDisplayDate(it.date)
        }

    private fun convertToDisplayDate(domainTime: DomainTime) =
        "${domainTime.day} ${domainTimeConverter.getMonthName(domainTime.month)}, ${domainTime.year}"

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

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionInteractor.execute(id)
        }
    }
}
