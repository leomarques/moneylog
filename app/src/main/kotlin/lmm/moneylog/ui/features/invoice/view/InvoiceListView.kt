package lmm.moneylog.ui.features.invoice.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.invoice.viewmodel.InvoiceListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun InvoiceListView(
    onItemClick: (Int) -> Unit,
    onFabClick: (Int) -> Unit,
    viewModel: InvoiceListViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    InvoiceListLayout(
        model = uiState,
        onItemClick = onItemClick,
        onFabClick = {
            onFabClick(uiState.cardId)
        },
        onArrowBackClick = onArrowBackClick,
        onPay = { id ->
            viewModel.onPay(
                id = id,
                onError = { showToast(context, R.string.error_pay_invoice) },
                onSuccess = { showToast(context, R.string.success_pay_invoice) }
            )
        },
        onPreviousMonthClick = viewModel::onPreviousMonthClick,
        onNextMonthClick = viewModel::onNextMonthClick
    )
}
