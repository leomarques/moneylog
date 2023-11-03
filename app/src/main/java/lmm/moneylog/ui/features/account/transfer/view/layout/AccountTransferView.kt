package lmm.moneylog.ui.features.account.transfer.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.features.account.transfer.viewmodel.AccountTransferViewModel
import lmm.moneylog.ui.features.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountTransferView(
    viewModel: AccountTransferViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val current = LocalContext.current

    AccountTransferLayout(
        value = uiState.value,
        accounts = uiState.accounts,
        originAccountDisplay = uiState.originAccountDisplay,
        destinationAccountDisplay = uiState.destinationAccountDisplay,
        originAccountColor = uiState.originAccountColor,
        destinationAccountColor = uiState.destinationAccountColor,
        onArrowBackClick = onArrowBackClick,
        onOriginAccountPicked = { viewModel.onOriginAccountPicked(it) },
        onDestinationAccountPicked = { viewModel.onDestinationAccountPicked(it) },
        onValueChange = { viewModel.onValueChange(it) },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { showToast(current, it) }
            )
        }
    )
}
