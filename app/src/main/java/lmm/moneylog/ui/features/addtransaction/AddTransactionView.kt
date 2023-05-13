package lmm.moneylog.ui.features.addtransaction

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionView(
    onArrowBackClick: () -> Unit,
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val current = LocalContext.current
    val errorText = stringResource(R.string.addtransaction_invalidvalue)

    AddTransactionLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = { transactionModel ->
            viewModel.saveTransaction(
                addTransactionModel = transactionModel,
                onValueError = {
                    Toast.makeText(current, errorText, Toast.LENGTH_LONG).show()
                }
            )
            onArrowBackClick()
        },
        addTransactionModel = viewModel.addTransactionModel,
        onDatePicked = { datePicked ->
            viewModel.onDatePicked(datePicked)
        },
        onTypeOfValueSelected = { isIncome ->
            viewModel.onTypeOfValueSelected(isIncome)
        }
    )
}
