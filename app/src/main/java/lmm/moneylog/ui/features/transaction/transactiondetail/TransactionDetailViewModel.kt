package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.GetTransactionRepository
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTimeConverter

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getTransactionRepository: GetTransactionRepository,
    private val addTransactionRepository: AddTransactionRepository,
    private val updateTransactionRepository: UpdateTransactionRepository,
    private val deleteTransactionRepository: DeleteTransactionRepository,
    private val domainTimeConverter: DomainTimeConverter
) : ViewModel() {

    var model = provideDefaultModel()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getTransactionRepository.getTransactionById(id)?.let { transaction ->
                    model = transaction.toDetailModel(domainTimeConverter)
                }
            }
        }
    }

    private fun provideDefaultModel() =
        TransactionDetailModel().apply {
            updateTime(domainTimeConverter.getCurrentTimeStamp(), domainTimeConverter)
        }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionRepository.delete(id)
        }
    }

    fun onTypeOfValueSelected(isIncome: Boolean) {
        model.isIncome.value = isIncome
    }

    fun onDatePicked(timeStamp: Long) {
        model.updateTime(timeStamp, domainTimeConverter)
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            viewModelScope.launch {
                if (model.isEdit) {
                    updateTransactionRepository.update(model.toTransaction())
                } else {
                    addTransactionRepository.save(model.toTransaction())
                }
                onSuccess()
            }
        } catch (e: NumberFormatException) {
            onError()
        }
    }
}
