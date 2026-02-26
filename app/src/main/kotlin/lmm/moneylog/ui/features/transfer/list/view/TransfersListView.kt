package lmm.moneylog.ui.features.transfer.list.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.transfer.list.viewmodel.TransfersListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransfersListView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val viewModel = koinViewModel<TransfersListViewModel>()
    val transfersModel by viewModel.uiState.collectAsState()

    TransfersListLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        onItemClick = onItemClick,
        model = transfersModel,
        onPreviousMonthClick = viewModel::onPreviousMonthClick,
        onNextMonthClick = viewModel::onNextMonthClick
    )
}
