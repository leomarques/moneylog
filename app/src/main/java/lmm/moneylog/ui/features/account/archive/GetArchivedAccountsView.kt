package lmm.moneylog.ui.features.account.archive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetArchivedAccountsView(
    viewModel: GetArchivedAccountsViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GetArchivedAccountsLayout(
        onArrowBackClick = onArrowBackClick,
        list = uiState.list,
        onUnArchive = { id ->
            viewModel.unarchive(id)
        },
        onDeleteConfirm = { id ->
            viewModel.delete(id)
        }
    )
}
