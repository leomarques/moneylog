package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountsListView(
    viewModel: AccountsListViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AccountsListLayout(
        list = uiState.list,
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        onItemClick = onItemClick,
        onArchivedIconClick = onArchivedIconClick,
        onTransferIconClick = onTransferIconClick
    )
}
