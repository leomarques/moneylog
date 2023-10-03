package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow(TransactionDetailModel())
    val uiState: StateFlow<TransactionDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val idParam = savedStateHandle.getIdParam()

            if (idParam != null) {
                getTransactionRepository.getTransactionById(idParam)?.let { transaction ->
                    _uiState.update {
                        transaction.toDetailModel(domainTimeConverter)
                    }
                }
            } else {
                _uiState.update {
                    val currentDate = with(domainTimeConverter) {
                        timeStampToDomainTime(getCurrentTimeStamp())
                    }
                    TransactionDetailModel(
                        date = currentDate,
                        displayDate = currentDate.convertToDisplayDate(domainTimeConverter)
                    )
                }
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            deleteTransactionRepository.delete(_uiState.value.id)
        }
    }

    fun onDatePicked(timeStamp: Long) {
        _uiState.update {
            val domainTime = domainTimeConverter.timeStampToDomainTime(timeStamp)
            it.copy(
                date = domainTime,
                displayDate = domainTime.convertToDisplayDate(domainTimeConverter)
            )
        }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            val transaction = _uiState.value.toTransaction()
            viewModelScope.launch {
                if (_uiState.value.isEdit) {
                    updateTransactionRepository.update(transaction)
                } else {
                    addTransactionRepository.save(transaction)
                }
                onSuccess()
            }
        } catch (e: NumberFormatException) {
            onError()
        }
    }
}
