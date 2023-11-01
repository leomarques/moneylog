package lmm.moneylog.ui.features.account.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetAccountsView(
    viewModel: GetAccountsViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GetAccountsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        list = uiState.list,
        onItemClick = onItemClick,
        onArchivedIconClick = onArchivedIconClick,
        onTransferIconClick = onTransferIconClick
    )
}
