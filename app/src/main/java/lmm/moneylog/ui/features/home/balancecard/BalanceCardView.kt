package lmm.moneylog.ui.features.home.balancecard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun BalanceCardView(
    onClick: (String) -> Unit,
    viewModel: BalanceCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BalanceCard(
        total = uiState.total,
        credit = uiState.credit,
        debt = uiState.debt,
        onClick = onClick
    )
}
