package lmm.moneylog.addtransaction

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTransactionScreen(
    onArrowBackClick: () -> Unit
) {
    val viewModel: AddTransactionViewModel = getViewModel()

    AddTransactionLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = { transactionModel ->
            viewModel.saveTransaction(transactionModel)
            onArrowBackClick()
        }
    )
}
