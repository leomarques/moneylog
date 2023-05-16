package lmm.moneylog.ui.features.gettransactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetTransactionsView(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    viewModel: GetTransactionsViewModel = koinViewModel(),
    typeOfValue: String?
) {
    val transactionsModel by viewModel.getTransactionsModel(typeOfValue).observeAsState(
        GetTransactionsModel(
            emptyList(),
            R.string.gettransactions_topbar_all
        )
    )

    GetTransactionsLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = transactionsModel
    )
}
