package lmm.moneylog.ui.features.account.detail.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.features.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: AccountDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val current = LocalContext.current

    AccountDetailLayout(
        name = uiState.name,
        color = uiState.color,
        isEdit = uiState.isEdit,
        showFab = uiState.showFab,
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { showToast(current, it) }
            )
        },
        onArchiveConfirm = {
            onArrowBackClick()
            viewModel.archiveAccount()
        },
        onColorPick = { viewModel.onColorPick(it) },
        onNameChange = { viewModel.onNameChange(it) }
    )
}
