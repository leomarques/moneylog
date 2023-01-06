package lmm.moneylog.addtransaction.ui

import androidx.compose.runtime.Composable
import lmm.moneylog.addtransaction.AddTransactionViewModel
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
