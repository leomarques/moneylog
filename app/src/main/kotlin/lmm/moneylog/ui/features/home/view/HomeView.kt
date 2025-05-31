package lmm.moneylog.ui.features.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    onFabClick: () -> Unit,
    onEmptyCardsClick: () -> Unit,
    onClick: (String) -> Unit,
    onCreditCardClick: (Int, String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    onSettingsClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeLayout(
        hideValues = uiState,
        onFabClick = onFabClick,
        onBalanceClick = onClick,
        onHideClick = { viewModel.onHideToggle() },
        onCreditCardClick = onCreditCardClick,
        onEmptyCardsClick = onEmptyCardsClick,
        onSettingsClick = onSettingsClick
    )
}
