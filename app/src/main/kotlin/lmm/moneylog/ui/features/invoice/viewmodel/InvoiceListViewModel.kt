package lmm.moneylog.ui.features.invoice.viewmodel

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
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.transaction.detail.viewmodel.nextCode
import lmm.moneylog.ui.features.transaction.detail.viewmodel.previousCode
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE

class InvoiceListViewModel(
    savedStateHandle: SavedStateHandle,
    private val updateTransactionRepository: UpdateTransactionRepository,
    private val getTransactionsRepository: GetTransactionsRepository,
    getCreditCardsRepository: GetCreditCardsRepository,
    getCategoriesRepository: GetCategoriesRepository,
    getAccountsRepository: GetAccountsRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InvoiceListUIState(titleResourceId = R.string.invoice))
    val uiState: StateFlow<InvoiceListUIState> = _uiState.asStateFlow()

    private lateinit var savedTransactions: List<Transaction>
    private lateinit var accounts: List<Account>
    private lateinit var cardName: String
    private lateinit var categoriesMap: Map<Int, String>
    private lateinit var categoriesColorMap: Map<Int, Color>
    private lateinit var invoiceCode: String
    var creditCardId: Int = -1

    init {
        viewModelScope.launch {
            invoiceCode = savedStateHandle.get<String>(PARAM_INVOICE_CODE) ?: ""
            creditCardId = savedStateHandle.get<String>(PARAM_CARD_ID)?.toInt() ?: -1

            if (invoiceCode.isEmpty() || creditCardId == -1) return@launch

            val categories = getCategoriesRepository.getCategoriesSuspend()

            categoriesMap =
                categories.associate {
                    it.id to it.name
                }
            categoriesColorMap =
                categories.associate {
                    it.id to Color(it.color.toULong())
                }

            accounts = getAccountsRepository.getAccountsSuspend()

            getCreditCardsRepository.getCreditCardById(creditCardId)?.let {
                cardName = it.name
            } ?: return@launch

            updateTransactions()
        }
    }

    private suspend fun updateTransactions() {
        getTransactionsRepository.getTransactionsByInvoice(
            invoiceCode = invoiceCode,
            creditCardId = creditCardId
        ).collect { transactions ->
            savedTransactions = transactions

            val totalValue = transactions.sumOf { it.value }

            _uiState.update {
                transactions.toInvoiceListUiState(
                    titleResourceId = R.string.invoice,
                    accounts = accounts,
                    categoriesMap = categoriesMap,
                    categoriesColorMap = categoriesColorMap,
                ).copy(
                    cardName = cardName,
                    name = domainTimeRepository.getInvoiceNameFromCode(invoiceCode),
                    totalValue = totalValue.formatForRs(false),
                    isInvoicePaid = transactions.all { it.accountId != null }
                )
            }
        }
    }

    fun onPay(
        id: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                pay(id)
                hideLoading()

                onSuccess()
            } catch (e: Exception) {
                hideLoading()

                onError()
            }
        }
    }

    private suspend fun pay(id: Int) {
        updateTransactionRepository.payInvoice(
            paramAccountId = id,
            transactions = savedTransactions,
            paramDate = domainTimeRepository.getCurrentDomainTime()
        )
    }

    private fun showLoading() {
        _uiState.update {
            it.copy(
                isLoadingWhilePay = true
            )
        }
    }

    private fun hideLoading() {
        _uiState.update {
            it.copy(
                isLoadingWhilePay = false
            )
        }
    }

    fun onPreviousMonthClick() {
        invoiceCode = invoiceCode.previousCode(domainTimeRepository)
        viewModelScope.launch {
            updateTransactions()
        }
    }

    fun onNextMonthClick() {
        invoiceCode = invoiceCode.nextCode(domainTimeRepository)
        viewModelScope.launch {
            updateTransactions()
        }
    }
}
