package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.account.detail.view.components.AdjustBalanceConfirmDialog
import lmm.moneylog.ui.features.account.detail.view.components.AdjustBalanceDialog
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountsListView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    viewModel: AccountsListViewModel = koinViewModel(),
    onTransferIconClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showAdjustBalanceDialog by remember { mutableStateOf(false) }
    var showAdjustBalanceConfirmDialog by remember { mutableStateOf(false) }
    var selectedAccountId by remember { mutableIntStateOf(-1) }
    var adjustmentValueDisplay by remember { mutableStateOf("") }
    var adjustmentValue by remember { mutableDoubleStateOf(0.0) }

    if (showAdjustBalanceDialog) {
        AdjustBalanceDialog(
            onConfirm = { newBalance ->
                MainScope().launch {
                    val result = viewModel.calculateAdjustment(selectedAccountId, newBalance)
                    if (result != null) {
                        adjustmentValueDisplay = result.first
                        adjustmentValue = result.second
                        showAdjustBalanceDialog = false
                        showAdjustBalanceConfirmDialog = true
                    } else {
                        showToast(context, R.string.validation_invalid_value)
                    }
                }
            },
            onDismiss = {
                showAdjustBalanceDialog = false
                selectedAccountId = -1
            }
        )
    }

    if (showAdjustBalanceConfirmDialog) {
        AdjustBalanceConfirmDialog(
            adjustmentValue = adjustmentValueDisplay,
            onConfirm = {
                viewModel.onAdjustBalanceConfirm(
                    accountId = selectedAccountId,
                    adjustmentValue = adjustmentValue,
                    onSuccess = {
                        showToast(context, R.string.account_adjust_balance_success)
                        showAdjustBalanceConfirmDialog = false
                        selectedAccountId = -1
                    },
                    onError = {
                        showToast(context, it)
                        showAdjustBalanceConfirmDialog = false
                        selectedAccountId = -1
                    }
                )
            },
            onDismiss = {
                showAdjustBalanceConfirmDialog = false
                showAdjustBalanceDialog = true
            }
        )
    }

    AccountsListLayout(
        list = uiState.list,
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        onItemClick = onItemClick,
        onArchivedIconClick = onArchivedIconClick,
        onTransferIconClick = onTransferIconClick,
        onAdjustBalanceClick = { accountId ->
            selectedAccountId = accountId
            showAdjustBalanceDialog = true
        }
    )
}
