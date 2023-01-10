package lmm.moneylog.features.addtransaction

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionScreen(
    onArrowBackClick: () -> Unit,
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    AddTransactionLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = { transactionModel ->
            viewModel.saveTransaction(transactionModel)
            onArrowBackClick()
        }
    )
}
