package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GetTransactionsView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    typeOfValue: String?,
    onItemClick: (Int) -> Unit
) {
    val viewModel = getViewModel<GetTransactionsViewModel>(
        parameters = { parametersOf(typeOfValue) }
    )
    val transactionsModel by viewModel.uiState.collectAsState()

    GetTransactionsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = transactionsModel,
        onItemClick = onItemClick
    )
}
