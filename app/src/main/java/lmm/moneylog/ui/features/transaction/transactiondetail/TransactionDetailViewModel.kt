package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.GetTransactionRepository
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTimeConverter

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getTransactionInteractor: GetTransactionRepository,
    private val addTransactionInteractor: AddTransactionRepository,
    private val updateTransactionInteractor: UpdateTransactionRepository,
    private val deleteTransactionInteractor: DeleteTransactionRepository,
    private val domainTimeConverter: DomainTimeConverter
) : ViewModel() {

    val transactionDetailModel = savedStateHandle.getIdParam()?.let { id ->
        getTransactionInteractor.getTransactionById(id).asLiveData().map { transaction ->
            transaction?.toDetailModel(domainTimeConverter) ?: provideDefaultModel()
        }
    } ?: MutableLiveData(
        provideDefaultModel()
    )

    private fun provideDefaultModel() =
        TransactionDetailModel().apply {
            updateTime(domainTimeConverter.getCurrentTimeStamp(), domainTimeConverter)
        }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionInteractor.delete(id)
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
            transactionDetailModel.value?.let {
                viewModelScope.launch {
                    if (it.isEdit) {
                        updateTransactionInteractor.update(it.toTransaction())
                    } else {
                        addTransactionInteractor.save(it.toTransaction())
                    }
                    onSuccess()
                }
            }
        } catch (e: NumberFormatException) {
            onError()
        }
    }
}
