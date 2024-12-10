package lmm.moneylog.ui.features.invoice.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.invoice.viewmodel.InvoiceListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun InvoiceListView(
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: InvoiceListViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    InvoiceListLayout(
        model = uiState,
        onItemClick = onItemClick,
        onFabClick = onFabClick,
        onArrowBackClick = onArrowBackClick
    )
}
