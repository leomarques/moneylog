package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTimeConverter

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val addTransactionRepository: AddTransactionRepository,
    private val updateTransactionRepository: UpdateTransactionRepository,
    private val deleteTransactionRepository: DeleteTransactionRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
    private val domainTimeConverter: DomainTimeConverter
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionDetailModel())
    val uiState: StateFlow<TransactionDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val idParam = savedStateHandle.getIdParam()

            if (idParam != null) {
                getTransactionsRepository.getTransactionById(idParam)?.let { transaction ->
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

            val accounts = getAccountsRepository.getAccountsSuspend()
            val categories = getCategoriesRepository.getCategoriesSuspend()

            val displayAccount = accounts.firstOrNull {
                it.id == _uiState.value.accountId
            }?.name

            val displayCategory = categories.firstOrNull {
                it.id == _uiState.value.categoryId
            }?.name

            _uiState.update {
                it.copy(
                    accounts = accounts,
                    categories = categories,
                    displayAccount = displayAccount.orEmpty(),
                    displayCategory = displayCategory.orEmpty()
                )
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

    fun onAccountPicked(index: Int) {
        _uiState.update {
            it.copy(
                displayAccount = _uiState.value.accounts[index].name,
                accountId = _uiState.value.accounts[index].id
            )
        }
    }

    fun onCategoryPicked(index: Int) {
        _uiState.update {
            it.copy(
                displayCategory = _uiState.value.categories[index].name,
                categoryId = _uiState.value.categories[index].id
            )
        }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        try {
            val transaction = _uiState.value.toTransaction()
            if (transaction.accountId == null) {
                onError(R.string.detailtransaction_no_account)
            } else {
                viewModelScope.launch {
                    if (_uiState.value.isEdit) {
                        updateTransactionRepository.update(transaction)
                    } else {
                        addTransactionRepository.save(transaction)
                    }
                    onSuccess()
                }
            }
        } catch (e: NumberFormatException) {
            onError(R.string.detailtransaction_invalidvalue)
        }
    }
}
