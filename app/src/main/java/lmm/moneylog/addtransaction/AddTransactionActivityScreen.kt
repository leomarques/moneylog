package lmm.moneylog.addtransaction

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTransactionActivityScreen(onBtnClick: () -> Unit) {
    val viewModel: AddTransactionViewModel = getViewModel()

    AddTransactionScreen { transactionModel ->
        viewModel.onBtnClick(transactionModel)
        onBtnClick()
    }
}
