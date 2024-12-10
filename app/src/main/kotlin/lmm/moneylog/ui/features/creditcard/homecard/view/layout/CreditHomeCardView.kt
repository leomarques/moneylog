package lmm.moneylog.ui.features.creditcard.homecard.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import lmm.moneylog.ui.features.creditcard.homecard.viewmodel.CreditHomeCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreditHomeCardView(
    onClick: (Int, String) -> Unit,
    onEmptyCardsClick: () -> Unit,
    hideValues: Boolean,
    modifier: Modifier = Modifier,
    viewModel: CreditHomeCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CreditHomeCardLayout(
        modifier = modifier,
        hideValues = hideValues,
        onClick = { id ->
            onClick(id, uiState.invoiceCode)
        },
        cards = uiState.cards,
        onEmptyCardsClick = onEmptyCardsClick
    )
}
