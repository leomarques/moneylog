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
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE

class InvoiceListViewModel(
    savedStateHandle: SavedStateHandle,
    getTransactionsRepository: GetTransactionsRepository,
    getCreditCardsRepository: GetCreditCardsRepository,
    getCategoriesRepository: GetCategoriesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(InvoiceListUIState(titleResourceId = R.string.invoice))
    val uiState: StateFlow<InvoiceListUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val invoiceCode = savedStateHandle.get<String>(PARAM_INVOICE_CODE)
            val creditCardId = savedStateHandle.get<String>(PARAM_CARD_ID)?.toInt()

            if (invoiceCode == null || creditCardId == null) return@launch

            val categories = getCategoriesRepository.getCategoriesSuspend()
            val card = getCreditCardsRepository.getCreditCardById(creditCardId)

            val categoriesMap =
                categories.associate {
                    it.id to it.name
                }
            val categoriesColorMap =
                categories.associate {
                    it.id to Color(it.color.toULong())
                }

            getTransactionsRepository.getTransactionsByInvoice(
                invoiceCode = invoiceCode,
                creditCardId = creditCardId
            ).collect { transactions ->
                val totalValue = transactions.sumOf { it.value }

                _uiState.update {
                    transactions.toInvoiceListUiState(
                        titleResourceId = R.string.invoice,
                        categoriesMap = categoriesMap,
                        categoriesColorMap = categoriesColorMap
                    ).copy(
                        cardId = creditCardId,
                        invoiceCode = invoiceCode,
                        cardName = card?.name.orEmpty(),
                        totalValue = totalValue.formatForRs(),
                        isInvoicePaid = transactions.all { it.accountId != null }
                    )
                }
            }
        }
    }

    fun onPayClick() {
    }
}
