package lmm.moneylog.ui.features.balancecard.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.balancecard.viewmodel.BalanceCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BalanceCardView(
    onClick: (String) -> Unit,
    hideValues: Boolean,
    viewModel: BalanceCardViewModel = koinViewModel(),
    onHideClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BalanceCardLayout(
        total = uiState.total,
        credit = uiState.credit,
        debt = uiState.debt,
        month = uiState.month,
        hideValues = hideValues,
        onHideClick = onHideClick,
        onClick = onClick
    )
}
