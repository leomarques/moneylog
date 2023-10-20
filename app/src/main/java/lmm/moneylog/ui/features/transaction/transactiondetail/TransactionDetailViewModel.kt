package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.ui.graphics.Color
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

            val account = accounts.firstOrNull {
                it.id == _uiState.value.accountId
            }?.let {
                it.name to Color(it.color.toULong())
            }
            val category = categories.firstOrNull {
                it.id == _uiState.value.categoryId
            }?.let {
                it.name to Color(it.color.toULong())
            }

            _uiState.update {
                it.copy(
                    accounts = accounts,
                    categories = categories,
                    displayAccount = account?.first.orEmpty(),
                    displayCategory = category?.first.orEmpty(),
                    displayAccountColor = account?.second ?: Color.Gray,
                    displayCategoryColor = category?.second ?: Color.Gray
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
            val account = _uiState.value.accounts[index]
            it.copy(
                displayAccount = account.name,
                displayAccountColor = Color(account.color.toULong()),
                accountId = account.id
            )
        }
    }

    fun onCategoryPicked(index: Int) {
        _uiState.update {
            val category = _uiState.value.categories[index]
            it.copy(
                displayCategory = category.name,
                displayCategoryColor = Color(category.color.toULong()),
                categoryId = category.id
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
                if (transaction.categoryId == null) {
                    onError(R.string.detailtransaction_no_category)
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
            }
        } catch (e: NumberFormatException) {
            onError(R.string.detailtransaction_invalidvalue)
        }
    }

    fun onIsIncomeSelected() {
        _uiState.update {
            it.copy(
                displayCategory = "",
                displayCategoryColor = Color.Gray,
                categoryId = null
            )
        }
    }
}
