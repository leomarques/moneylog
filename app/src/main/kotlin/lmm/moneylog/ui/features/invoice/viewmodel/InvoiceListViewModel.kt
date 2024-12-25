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
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE

class InvoiceListViewModel(
    savedStateHandle: SavedStateHandle,
    private val updateTransactionRepository: UpdateTransactionRepository,
    getTransactionsRepository: GetTransactionsRepository,
    getCreditCardsRepository: GetCreditCardsRepository,
    getCategoriesRepository: GetCategoriesRepository,
    getAccountsRepository: GetAccountsRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InvoiceListUIState(titleResourceId = R.string.invoice))
    val uiState: StateFlow<InvoiceListUIState> = _uiState.asStateFlow()

    private lateinit var savedTransactions: List<Transaction>

    init {
        viewModelScope.launch {
            val (invoiceCode, creditCardId) = getData(savedStateHandle)
            if (invoiceCode == null || creditCardId == null) return@launch

            getTransactionsRepository.getTransactionsByInvoice(
                invoiceCode = invoiceCode,
                creditCardId = creditCardId
            ).collect { transactions ->
                savedTransactions = transactions

                val totalValue = transactions.sumOf { it.value }

                val (categoriesMap, categoriesColorMap) = getCategoriesMap(getCategoriesRepository)
                val accounts = getAccountsRepository.getAccountsSuspend()
                val card = getCreditCardsRepository.getCreditCardById(creditCardId)

                _uiState.update {
                    transactions.toInvoiceListUiState(
                        titleResourceId = R.string.invoice,
                        accounts = accounts,
                        categoriesMap = categoriesMap,
                        categoriesColorMap = categoriesColorMap
                    ).copy(
                        cardId = creditCardId,
                        invoiceCode = invoiceCode,
                        cardName = card?.name.orEmpty(),
                        totalValue = totalValue.formatForRs(false),
                        isInvoicePaid = transactions.all { it.accountId != null }
                    )
                }
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

    private suspend fun getCategoriesMap(getCategoriesRepository: GetCategoriesRepository): Pair<Map<Int, String>, Map<Int, Color>> {
        val categories = getCategoriesRepository.getCategoriesSuspend()

        val categoriesMap =
            categories.associate {
                it.id to it.name
            }
        val categoriesColorMap =
            categories.associate {
                it.id to Color(it.color.toULong())
            }
        return Pair(categoriesMap, categoriesColorMap)
    }

    private fun getData(savedStateHandle: SavedStateHandle): Pair<String?, Int?> {
        val invoiceCode = savedStateHandle.get<String>(PARAM_INVOICE_CODE)
        val creditCardId = savedStateHandle.get<String>(PARAM_CARD_ID)?.toInt()
        return Pair(invoiceCode, creditCardId)
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
}
