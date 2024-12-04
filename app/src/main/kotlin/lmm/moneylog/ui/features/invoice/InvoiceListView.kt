package lmm.moneylog.ui.features.invoice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun InvoiceListView(
    invoiceCode: String,
    creditCardId: Int,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    onArrowBackClick: () -> Unit
) {
    val viewModel =
        koinViewModel<InvoiceListViewModel>(parameters = { parametersOf(invoiceCode, creditCardId) })
    val uiState by viewModel.uiState.collectAsState()

    InvoiceListLayout(
        model = uiState,
        onItemClick = onItemClick,
        onFabClick = onFabClick,
        onArrowBackClick = onArrowBackClick
    )
}
