package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.account.archive.viewmodel.ArchivedAccountsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchivedAccountsListView(
    viewModel: ArchivedAccountsViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ArchivedAccountsLayout(
        list = uiState,
        onArrowBackClick = onArrowBackClick,
        onUnArchive = { viewModel.unarchive(it) },
        onDeleteConfirm = { viewModel.delete(it) }
    )
}
