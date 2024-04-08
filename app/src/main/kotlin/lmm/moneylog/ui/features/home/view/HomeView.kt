package lmm.moneylog.ui.features.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    onFabClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    onClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeLayout(
        onFabClick = onFabClick,
        onClick = onClick,
        hideValues = uiState,
        onHideClick = { viewModel.onHideToggle() }
    )
}
