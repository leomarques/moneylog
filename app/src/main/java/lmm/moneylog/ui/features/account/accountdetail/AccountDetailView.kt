package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: AccountDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AccountDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick()
            onArrowBackClick()
        },
        isEdit = uiState.isEdit,
        valueState = uiState.name,
        onDeleteConfirmClick = {
            viewModel.deleteAccount()
            onArrowBackClick()
        }
    )
}
