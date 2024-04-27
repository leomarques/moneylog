package lmm.moneylog.ui.features.creditcardcard.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.creditcardcard.viewmodel.CreditCardCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreditCardCardView(
    onClick: (String) -> Unit,
    hideValues: Boolean,
    viewModel: CreditCardCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CreditCardCardLayout(
        total = uiState.total,
        hideValues = hideValues,
        onClick = onClick
    )
}
