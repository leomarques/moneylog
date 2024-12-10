package lmm.moneylog.ui.features.transaction.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.data.invoice.repositories.GetInvoicesRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.orDefaultColor
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.theme.neutralColor

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getAccountsRepository: GetAccountsRepository,
    getCategoriesRepository: GetCategoriesRepository,
    getCreditCardsRepository: GetCreditCardsRepository,
    getInvoicesRepository: GetInvoicesRepository,
    private val addTransactionRepository: AddTransactionRepository,
    private val updateTransactionRepository: UpdateTransactionRepository,
    private val deleteTransactionRepository: DeleteTransactionRepository,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionDetailUIState())
    val uiState: StateFlow<TransactionDetailUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accountsAsync = async { getAccountsRepository.getAccountsSuspend() }
            val categoriesAsync = async { getCategoriesRepository.getCategoriesSuspend() }
            val creditCardsAsync = async { getCreditCardsRepository.getCreditCardsSuspend() }
            val invoices = getInvoicesRepository.getInvoices()

            val idParam = savedStateHandle.getIdParam()
            if (idParam != null) {
                setupEdit(idParam = idParam)
            } else {
                setupAdd(
                    accountsAsync = accountsAsync,
                    categoriesAsync = categoriesAsync,
                    invoices = invoices
                )
            }

            val accounts = accountsAsync.await()
            val categories = categoriesAsync.await()
            val creditCards = creditCardsAsync.await()
            val account = accounts.getAccountById(_uiState.value.accountId)
            val category = categories.getCategoryById(_uiState.value.categoryId)
            val creditCard = creditCards.getCreditCardById(_uiState.value.creditCardId)

            _uiState.update {
                it.copy(
                    accounts = accounts,
                    categories = categories,
                    creditCards = creditCards,
                    invoices = invoices,
                    displayAccount = account?.first.orEmpty(),
                    displayCategory = category?.first.orEmpty(),
                    displayCreditCard = creditCard?.first.orEmpty(),
                    displayAccountColor = account?.second.orDefaultColor(),
                    displayCategoryColor = category?.second.orDefaultColor(),
                    displayCreditCardColor = creditCard?.second.orDefaultColor()
                )
            }
        }
    }

    private suspend fun setupEdit(idParam: Int) {
        getTransactionsRepository.getTransactionById(idParam)?.let { transaction ->
            _uiState.update {
                transaction.toDetailModel(domainTimeRepository)
            }
        }
    }

    private suspend fun setupAdd(
        accountsAsync: Deferred<List<Account>>,
        categoriesAsync: Deferred<List<Category>>,
        invoices: List<Invoice>
    ) {
        _uiState.update {
            val currentDate = domainTimeRepository.getCurrentDomainTime()
            val accountId = accountsAsync.await().firstOrNull()?.id
            val categoryId = categoriesAsync.await().firstOrNull()?.id
            val invoice = invoices[1]

            TransactionDetailUIState(
                displayDate = currentDate.convertToDisplayDate(domainTimeRepository),
                accountId = accountId,
                categoryId = categoryId,
                displayInvoice = invoice.name,
                date = currentDate,
            )
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            deleteTransactionRepository.delete(_uiState.value.id)
        }
    }

    fun onIsIncomeSelect(isIncome: Boolean) {
        _uiState.update {
            it.copy(
                isIncome = isIncome,
                categoryId = null,
                displayCategory = "",
                displayCategoryColor = neutralColor
            )
        }
    }

    fun onValueChange(value: String) {
        _uiState.update { it.copy(value = value) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDatePick(timeStamp: Long) {
        _uiState.update {
            val domainTime = domainTimeRepository.timeStampToDomainTime(timeStamp)
            it.copy(
                date = domainTime,
                displayDate = domainTime.convertToDisplayDate(domainTimeRepository)
            )
        }
    }

    fun onAccountPick(index: Int) {
        _uiState.update {
            with(_uiState.value.accounts[index]) {
                it.copy(
                    accountId = id,
                    displayAccount = name,
                    displayAccountColor = color.toComposeColor()
                )
            }
        }
    }

    fun onCategoryPick(index: Int) {
        val filteredCategories =
            with(_uiState.value) {
                categories.filter { it.isIncome == isIncome }
            }

        _uiState.update {
            with(filteredCategories[index]) {
                it.copy(
                    categoryId = id,
                    displayCategory = name,
                    displayCategoryColor = color.toComposeColor()
                )
            }
        }
    }

    fun onCreditCardPick(index: Int) {
        _uiState.update {
            with(_uiState.value.creditCards[index]) {
                it.copy(
                    creditCardId = id,
                    displayCreditCard = name,
                    displayCreditCardColor = color.toComposeColor()
                )
            }
        }
    }

    fun onInvoicePick(index: Int) {
        _uiState.update {
            val invoice = it.invoices[index]
            it.copy(
                displayInvoice = invoice.name,
                invoiceCode = invoice.getCode()
            )
        }
    }

    fun onDebtSelected() {
        _uiState.update {
            val firstOrNull = it.accounts.firstOrNull()
            it.copy(
                isDebtSelected = true,
                creditCardId = null,
                invoiceCode = null,
                accountId = firstOrNull?.id,
                displayAccount = firstOrNull?.name.orEmpty(),
                displayAccountColor = firstOrNull?.color?.toComposeColor().orDefaultColor()
            )
        }
    }

    fun onCreditSelected() {
        _uiState.update {
            val firstOrNull = it.creditCards.firstOrNull()
            val invoice = it.invoices[1]
            it.copy(
                isDebtSelected = false,
                accountId = null,
                creditCardId = firstOrNull?.id,
                displayCreditCard = firstOrNull?.name.orEmpty(),
                displayCreditCardColor = firstOrNull?.color?.toComposeColor().orDefaultColor(),
                invoiceCode = invoice.getCode(),
                displayInvoice = invoice.name,
                isIncome = false
            )
        }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        try {
            val state = _uiState.value
            with(state.toTransaction()) {
                if (state.isDebtSelected && accountId == null) {
                    onError(R.string.detail_no_account)
                    return
                }

                if (!state.isDebtSelected && creditCardId == null) {
                    onError(R.string.detail_no_cc)
                    return
                }

                if (categoryId == null) {
                    onError(R.string.detail_no_category)
                    return
                }

                _uiState.update { it.copy(showFab = false) }

                viewModelScope.launch {
                    if (state.isEdit) {
                        updateTransactionRepository.update(this@with)
                    } else {
                        addTransactionRepository.save(this@with)
                    }
                    onSuccess()
                }
            }
        } catch (e: NumberFormatException) {
            onError(R.string.detail_invalidvalue)
        }
    }
}
