package lmm.moneylog.ui.features.account.detail.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.account.detail.viewmodel.AccountDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailView(
    viewModel: AccountDetailViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
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
