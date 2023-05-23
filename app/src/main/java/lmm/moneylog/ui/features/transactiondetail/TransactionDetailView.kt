package lmm.moneylog.ui.features.transactiondetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.domain.time.DomainTime
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TransactionDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = koinViewModel()
) {
    val current = LocalContext.current
    val errorText = stringResource(R.string.detailtransaction_invalidvalue)

    val model by viewModel.transactionDetailModel.observeAsState(
        TransactionDetailModel(
            value = mutableStateOf(""),
            isIncome = mutableStateOf(true),
            displayDate = mutableStateOf(""),
            description = mutableStateOf(""),
            date = DomainTime(0, 0, 0),
            isEdit = false,
            id = 0,
            titleResourceId = R.string.detailtransaction_topbar_title_add
        )
    )

    TransactionDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = { transactionModel ->
            viewModel.onFabClick(
                transactionModel = transactionModel,
                onSuccess = onArrowBackClick,
                onError = { Toast.makeText(current, errorText, Toast.LENGTH_LONG).show() }
            )
        },
        transactionDetailModel = model,
        onDatePicked = { datePicked ->
            viewModel.onDatePicked(datePicked)
        },
        onTypeOfValueSelected = { isIncome ->
            viewModel.onTypeOfValueSelected(isIncome)
        }
    )
}
