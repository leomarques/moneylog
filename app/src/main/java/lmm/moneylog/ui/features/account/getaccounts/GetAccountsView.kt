package lmm.moneylog.ui.features.account.getaccounts

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
    onArchivedIconClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GetAccountsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        list = uiState.list,
        onItemClick = onItemClick,
        onArchivedIconClick = onArchivedIconClick
    )
}
