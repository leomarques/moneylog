package lmm.moneylog.addtransaction

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTransactionActivityScreen(onArrowBackClick: () -> Unit, onBtnClick: () -> Unit) {
    val viewModel: AddTransactionViewModel = getViewModel()

    AddTransactionScreen(
        onArrowBackClick = onArrowBackClick,
        onFabClick = { transactionModel ->
            viewModel.onBtnClick(transactionModel)
            onBtnClick()
        }
    )
}
