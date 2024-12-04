package lmm.moneylog.ui.features.invoice

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository

class InvoiceListViewModel(
    private val invoiceCode: String,
    private val creditCardId: Int,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(InvoiceListUIState(titleResourceId = R.string.invoice))
    val uiState: StateFlow<InvoiceListUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val categories = getCategoriesRepository.getCategoriesSuspend()

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
                _uiState.update {
                    transactions.toInvoiceListUiState(
                        titleResourceId = R.string.invoice,
                        categoriesMap = categoriesMap,
                        categoriesColorMap = categoriesColorMap
                    )
                }
            }
        }
    }
}
