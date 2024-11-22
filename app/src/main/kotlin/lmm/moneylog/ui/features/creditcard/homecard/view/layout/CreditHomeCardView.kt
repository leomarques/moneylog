package lmm.moneylog.ui.features.creditcard.homecard.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.creditcard.homecard.viewmodel.CreditHomeCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreditHomeCardView(
    onClick: () -> Unit,
    hideValues: Boolean,
    viewModel: CreditHomeCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CreditHomeCardLayout(
        total = uiState.total,
        hideValues = hideValues,
        onClick = onClick
    )
}
