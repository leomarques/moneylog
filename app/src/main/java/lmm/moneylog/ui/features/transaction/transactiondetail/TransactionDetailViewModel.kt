package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

    val transactionDetailModel: LiveData<TransactionDetailModel> =
        savedStateHandle.get<Int>("id")?.let { id ->
            getTransactionInteractor.getTransaction(id).asLiveData().map { transaction ->
                transaction?.toDetailModel(domainTimeConverter) ?:
                    provideDefaultModel(domainTimeConverter)
            }
        } ?: MutableLiveData(
            provideDefaultModel(domainTimeConverter)
        )

    private fun provideDefaultModel(domainTimeConverter: DomainTimeConverter) =
        TransactionDetailModel(
        ).apply {
            updateTime(domainTimeConverter.getCurrentTimeStamp(), domainTimeConverter)
        }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionInteractor.execute(id)
        }
    }

    fun onTypeOfValueSelected(isIncome: Boolean) {
        transactionDetailModel.value?.isIncome?.value = isIncome
    }

    fun onDatePicked(timeStamp: Long) {
        transactionDetailModel.value?.updateTime(timeStamp, domainTimeConverter)
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
                value = it.value.value.validateValue(it.isIncome.value),
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
                value = it.value.value.validateValue(it.isIncome.value),
                date = it.date,
                description = it.description.value
            )

            viewModelScope.launch {
                addTransactionInteractor.execute(transaction)
            }
        }
    }
}
