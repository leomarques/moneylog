package lmm.moneylog.ui.features.gettransactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetTransactionsView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    viewModel: GetTransactionsViewModel = koinViewModel(),
    typeOfValue: String?
) {
    val transactionsModel by viewModel.convertToModel(typeOfValue).observeAsState(
        GetTransactionsModel(emptyList())
    )

    GetTransactionsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = transactionsModel
    )
}
