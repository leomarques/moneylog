package lmm.moneylog.ui.features.transaction.list.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.transaction.list.viewmodel.TransactionsListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TransactionsListView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    typeOfValue: String?,
    onItemClick: (Int) -> Unit
) {
    val viewModel = koinViewModel<TransactionsListViewModel>(
        parameters = { parametersOf(typeOfValue) })
    val transactionsModel by viewModel.uiState.collectAsState()

    TransactionsListLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = transactionsModel,
        onItemClick = onItemClick
    )
}
