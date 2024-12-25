package lmm.moneylog.ui.features.transaction.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.data.invoice.repositories.GetInvoicesRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.orDefaultColor
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID

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
            val invoices = getInvoicesRepository.getInvoices()

            val accounts = getAccountsRepository.getAccountsSuspend()
            val categories = getCategoriesRepository.getCategoriesSuspend()
            val creditCards = getCreditCardsRepository.getCreditCardsSuspend()

            val idParam = savedStateHandle.getIdParam()
            if (idParam != null) {
                setupEdit(idParam = idParam)
            } else {
                val cardId = savedStateHandle.get<String>(PARAM_CARD_ID)

                setupAdd(
                    accounts = accounts,
                    categories = categories,
                    creditCards = creditCards,
                    invoices = invoices,
                    cardId = cardId
                )
            }

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

    private fun setupAdd(
        accounts: List<Account>,
        categories: List<Category>,
        creditCards: List<CreditCard>,
        invoices: List<Invoice>,
        cardId: String?
    ) {
        _uiState.update {
            val currentDate = domainTimeRepository.getCurrentDomainTime()
            val categoryId = categories.firstOrNull()?.id
            val invoice = invoices[1]

            if (cardId == null) {
                val accountId = accounts.firstOrNull()?.id

                TransactionDetailUIState(
                    displayDate = currentDate.convertToDisplayDate(domainTimeRepository),
                    accountId = accountId,
                    categoryId = categoryId,
                    displayInvoice = invoice.name,
                    date = currentDate
                )
            } else {
                val displayCreditCard =
                    creditCards.firstOrNull {
                        it.id == cardId.toInt()
                    }?.name ?: ""

                TransactionDetailUIState(
                    displayDate = currentDate.convertToDisplayDate(domainTimeRepository),
                    categoryId = categoryId,
                    creditCardId = cardId.toIntOrNull(),
                    displayCreditCard = displayCreditCard,
                    displayInvoice = invoice.name,
                    invoiceCode = invoice.getCode(),
                    date = currentDate,
                    isIncome = false,
                    isDebtSelected = false,
                )
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            deleteTransactionRepository.delete(_uiState.value.id)
        }
    }

    fun onIsIncomeSelect(isIncome: Boolean) {
        val category = _uiState.value.categories.firstOrNull { it.isIncome == isIncome }
        val displayCategory = category?.name ?: ""
        val displayCategoryColor = category?.color?.toComposeColor().orDefaultColor()

        _uiState.update {
            it.copy(
                isIncome = isIncome,
                categoryId = category?.id,
                displayCategory = displayCategory,
                displayCategoryColor = displayCategoryColor
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
        val category = _uiState.value.categories.firstOrNull { !it.isIncome }
        val displayCategory = category?.name ?: ""
        val displayCategoryColor = category?.color?.toComposeColor().orDefaultColor()

        val creditCard = _uiState.value.creditCards.firstOrNull()
        val displayCreditCard = creditCard?.name.orEmpty()
        val displayCreditCardColor = creditCard?.color?.toComposeColor().orDefaultColor()

        val invoice = _uiState.value.invoices[1]

        _uiState.update {
            it.copy(
                isDebtSelected = false,
                accountId = null,
                creditCardId = creditCard?.id,
                displayCreditCard = displayCreditCard,
                displayCreditCardColor = displayCreditCardColor,
                invoiceCode = invoice.getCode(),
                displayInvoice = invoice.name,
                isIncome = false,
                categoryId = category?.id,
                displayCategory = displayCategory,
                displayCategoryColor = displayCategoryColor
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
                if (checkForNulls(
                        state = state,
                        onError = onError
                    )
                ) {
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

    private fun Transaction.checkForNulls(
        state: TransactionDetailUIState,
        onError: (Int) -> Unit
    ): Boolean {
        if (state.isDebtSelected && accountId == null) {
            onError(R.string.detail_no_account)
            return true
        }

        if (!state.isDebtSelected && creditCardId == null) {
            onError(R.string.detail_no_cc)
            return true
        }

        if (categoryId == null) {
            onError(R.string.detail_no_category)
            return true
        }
        return false
    }
}
