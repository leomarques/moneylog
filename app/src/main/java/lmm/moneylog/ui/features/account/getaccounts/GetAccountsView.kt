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
    val model by viewModel.uiState.collectAsState()

    GetAccountsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = model,
        onItemClick = onItemClick,
        onArchivedIconClick = onArchivedIconClick
    )
}
