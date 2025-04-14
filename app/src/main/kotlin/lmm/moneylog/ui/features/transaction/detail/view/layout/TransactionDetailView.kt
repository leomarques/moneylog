package lmm.moneylog.ui.features.transaction.detail.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.transaction.detail.viewmodel.TransactionDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionDetailView(
    viewModel: TransactionDetailViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val current = LocalContext.current

    TransactionDetailLayout(
        uiState = uiState,
        onArrowBackClick = onArrowBackClick,
        onDeleteConfirmClick = {
            viewModel.deleteTransaction()
            onArrowBackClick()
        },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { stringId -> showToast(current, stringId) }
            )
        },
        onDatePick = { date -> viewModel.onDatePick(date) },
        onAccountPick = { index -> viewModel.onAccountPick(index) },
        onCategoryPick = { index -> viewModel.onCategoryPick(index) },
        onCreditCardPick = { index -> viewModel.onCreditCardPick(index) },
        onInvoicePick = { index -> viewModel.onInvoicePick(index) },
        onIsIncomeSelect = { viewModel.onIsIncomeSelect(it) },
        onValueChange = { viewModel.onValueChange(it) },
        onDescriptionChange = { viewModel.onDescriptionChange(it) },
        onDebtSelect = { viewModel.onDebtSelected() },
        onCreditSelect = { viewModel.onCreditSelected() }
    )
}
