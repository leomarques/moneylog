package lmm.moneylog.ui.features.creditcard.list.view.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.creditcard.list.viewmodel.CreditCardsListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreditCardsListView(
    onArrowBackClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: CreditCardsListViewModel = koinViewModel(),
    onFabClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CreditCardsListLayout(
        list = uiState.list,
        onArrowBackClick = onArrowBackClick,
        onItemClick = onItemClick,
        onFabClick = onFabClick
    )
}
