package lmm.moneylog.ui.features.invoice.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.invoice.view.components.AdjustInvoiceConfirmDialog
import lmm.moneylog.ui.features.invoice.view.components.AdjustInvoiceDialog
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
    val coroutineScope = rememberCoroutineScope()

    var showAdjustInvoiceDialog by remember { mutableStateOf(false) }
    var showAdjustInvoiceConfirmDialog by remember { mutableStateOf(false) }
    var adjustmentValue by remember { mutableStateOf("") }
    var adjustmentAmount by remember { mutableDoubleStateOf(0.0) }

    if (showAdjustInvoiceDialog) {
        AdjustInvoiceDialog(
            onDismiss = { showAdjustInvoiceDialog = false },
            onConfirm = { newValue ->
                coroutineScope.launch {
                    val result = viewModel.calculateInvoiceAdjustment(newValue)
                    if (result != null) {
                        adjustmentValue = result.first
                        adjustmentAmount = result.second
                        showAdjustInvoiceDialog = false
                        showAdjustInvoiceConfirmDialog = true
                    } else {
                        showToast(context, R.string.validation_invalid_value)
                    }
                }
            }
        )
    }

    if (showAdjustInvoiceConfirmDialog) {
        AdjustInvoiceConfirmDialog(
            adjustmentValue = adjustmentValue,
            onConfirm = {
                viewModel.onAdjustInvoiceConfirm(
                    adjustmentValue = adjustmentAmount,
                    onSuccess = {
                        showToast(context, R.string.invoice_adjust_value_success)
                        showAdjustInvoiceConfirmDialog = false
                    },
                    onError = { errorResId ->
                        showToast(context, errorResId)
                    }
                )
            },
            onDismiss = {
                showAdjustInvoiceConfirmDialog = false
            }
        )
    }

    InvoiceListLayout(
        model = uiState,
        onItemClick = onItemClick,
        onFabClick = {
            onFabClick(viewModel.creditCardId)
        },
        onArrowBackClick = onArrowBackClick,
        onPay = { id ->
            viewModel.onPay(
                id = id,
                onError = { showToast(context, R.string.error_invoice_pay) },
                onSuccess = { showToast(context, R.string.success_invoice_pay) }
            )
        },
        onAdjustClick = {
            showAdjustInvoiceDialog = true
        },
        onPreviousMonthClick = viewModel::onPreviousMonthClick,
        onNextMonthClick = viewModel::onNextMonthClick
    )
}
