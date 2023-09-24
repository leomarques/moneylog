package lmm.moneylog.ui.features.transaction.transactiondetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TransactionDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = koinViewModel()
) {
    val current = LocalContext.current
    val errorText = stringResource(R.string.detailtransaction_invalidvalue)

    TransactionDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { Toast.makeText(current, errorText, Toast.LENGTH_LONG).show() }
            )
        },
        transactionDetailModel = viewModel.model,
        onDatePicked = { datePicked ->
            viewModel.onDatePicked(datePicked)
        },
        onTypeOfValueSelected = { isIncome ->
            viewModel.onTypeOfValueSelected(isIncome)
        },
        onDeleteConfirmClick = { id ->
            viewModel.deleteTransaction(id)
            onArrowBackClick()
        }
    )
}
