package lmm.moneylog.ui.features.invoice.viewmodel

import android.util.Log
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
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.category.list.model.CategoriesListUIState
import lmm.moneylog.ui.features.category.list.viewmodel.toCategoryModelList
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.transaction.detail.viewmodel.nextCode
import lmm.moneylog.ui.features.transaction.detail.viewmodel.previousCode
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE

class InvoiceListViewModel(
    savedStateHandle: SavedStateHandle,
    private val updateTransactionRepository: UpdateTransactionRepository,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val addTransactionRepository: AddTransactionRepository,
    getCreditCardsRepository: GetCreditCardsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
    getAccountsRepository: GetAccountsRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    companion object {
        private const val TAG = "InvoiceListViewModel"
    }

    private val _uiState =
        MutableStateFlow(InvoiceListUIState(titleResourceId = R.string.common_invoice))
    val uiState: StateFlow<InvoiceListUIState> = _uiState.asStateFlow()

    private val _categoriesState = MutableStateFlow(CategoriesListUIState())
    val categoriesState: StateFlow<CategoriesListUIState> = _categoriesState.asStateFlow()

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

            // Update categories state for UI
            _categoriesState.update {
                CategoriesListUIState(
                    categories
                        .toCategoryModelList()
                        .reversed()
                )
            }

            accounts = getAccountsRepository.getAccountsSuspend()

            getCreditCardsRepository.getCreditCardById(creditCardId)?.let {
                cardName = it.name
            } ?: return@launch

            updateTransactions()
        }

        // Also listen for category updates
        viewModelScope.launch {
            getCategoriesRepository.getCategories().collect { categories ->
                _categoriesState.update {
                    CategoriesListUIState(
                        categories
                            .toCategoryModelList()
                            .reversed()
                    )
                }
            }
        }
    }

    private suspend fun updateTransactions() {
        getTransactionsRepository
            .getTransactionsByInvoice(
                invoiceCode = invoiceCode,
                creditCardId = creditCardId
            ).collect { transactions ->
                savedTransactions = transactions

                val totalValue = transactions.sumOf { it.value }

                _uiState.update {
                    transactions
                        .toInvoiceListUiState(
                            titleResourceId = R.string.common_invoice,
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
            } catch (_: Exception) {
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

    @Suppress("ReturnCount")
    fun calculateInvoiceAdjustment(newValue: String): Pair<String, Double>? {
        val newValueDouble = newValue.toDoubleOrNull() ?: return null

        val currentTotal = savedTransactions.sumOf { it.value }

        val adjustmentValue = newValueDouble - currentTotal
        if (adjustmentValue == 0.0) return null

        // Format the adjustment value for display
        val formattedValue =
            if (adjustmentValue > 0) {
                "+${adjustmentValue.formatForRs()}"
            } else {
                adjustmentValue.formatForRs()
            }

        return Pair(formattedValue, adjustmentValue)
    }

    fun onAdjustInvoiceConfirm(
        adjustmentValue: Double,
        categoryId: Int?,
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Extract invoice month and year from invoiceCode
                val invoiceMonth = invoiceCode.substringBefore(".").toInt()
                val invoiceYear = invoiceCode.substringAfter(".").toInt()

                val adjustmentTransaction =
                    Transaction(
                        value = adjustmentValue,
                        description = "Adjustment",
                        date = domainTimeRepository.getCurrentDomainTime(),
                        creditCardId = creditCardId,
                        invoiceMonth = invoiceMonth,
                        invoiceYear = invoiceYear,
                        categoryId = if (categoryId != null && categoryId > 0) categoryId else null
                    )
                addTransactionRepository.save(adjustmentTransaction)
                onSuccess()
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Error saving invoice adjustment transaction", e)
                onError(R.string.validation_invalid_data)
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Invalid argument for invoice adjustment transaction", e)
                onError(R.string.validation_invalid_data)
            }
        }
    }
}
