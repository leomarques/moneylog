package lmm.moneylog.ui.features.transaction.transactiondetail

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = koinViewModel()
) {
    val current = LocalContext.current
    val invalidValueErrorText = stringResource(R.string.detailtransaction_invalidvalue)
    val noAccountErrorText = stringResource(R.string.detailtransaction_no_account)
    val error = stringResource(R.string.error)

    val uiState by viewModel.uiState.collectAsState()

    TransactionDetailLayout(
        valueField = uiState.value,
        isIncomeField = uiState.isIncome,
        descriptionField = uiState.description,
        displayDate = uiState.displayDate,
        displayAccount = uiState.displayAccount,
        displayCategory = uiState.displayCategory,
        accounts = uiState.accounts.map { it.name },
        categories = uiState.categories.map { it.name },
        isEdit = uiState.isEdit,
        topBarTitle = stringResource(uiState.titleResourceId),
        onArrowBackClick = onArrowBackClick,
        onDeleteConfirmClick = {
            viewModel.deleteTransaction()
            onArrowBackClick()
        },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { stringId ->
                    Toast.makeText(
                        current,
                        when (stringId) {
                            R.string.detailtransaction_no_account ->
                                noAccountErrorText

                            R.string.detailtransaction_invalidvalue ->
                                invalidValueErrorText

                            else -> error
                        },
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        },
        onDatePicked = { datePicked ->
            viewModel.onDatePicked(datePicked)
        },
        onAccountPicked = { index ->
            viewModel.onAccountPicked(index)
        },
        onCategoryPicked = { index ->
            viewModel.onCategoryPicked(index)
        }
    )
}
