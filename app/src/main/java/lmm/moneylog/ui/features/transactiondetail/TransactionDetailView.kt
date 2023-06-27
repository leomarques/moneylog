package lmm.moneylog.ui.features.transactiondetail

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.data.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = koinViewModel()
) {
    val current = LocalContext.current
    val errorText = stringResource(R.string.detailtransaction_invalidvalue)

    val model by viewModel.transactionDetailModel.observeAsState(
        viewModel.provideDefaultModel()
    )

    TransactionDetailLayout(
        model = model,
        onArrowBackClick = onArrowBackClick,
        onDatePicked = { datePicked ->
            viewModel.onDatePicked(datePicked)
        },
        onTypeOfValueSelected = { isIncome ->
            viewModel.onTypeOfValueSelected(isIncome)
        },
        onDeleteConfirmClick = { id ->
            viewModel.deleteTransaction(id)
            onArrowBackClick()
        },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { Toast.makeText(current, errorText, Toast.LENGTH_LONG).show() }
            )
        }
    )
}
