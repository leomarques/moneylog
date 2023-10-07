package lmm.moneylog.ui.features.account.transfer

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountTransferView(
    viewModel: AccountTransferViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val current = LocalContext.current
    val invalidValueErrorText = stringResource(R.string.detailtransaction_invalidvalue)
    val noAccountErrorText = stringResource(R.string.detailtransaction_no_account)
    val error = stringResource(R.string.error)

    val uiState by viewModel.uiState.collectAsState()

    AccountTransferLayout(
        valueField = uiState.value,
        originAccountDisplay = uiState.originAccountDisplay,
        destinationAccountDisplay = uiState.destinationAccountDisplay,
        accounts = uiState.accounts.map { it.second },
        onArrowBackClick = onArrowBackClick,
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
        onOriginAccountPicked = { index ->
            viewModel.onOriginAccountPicked(index)
        },
        onDestinationAccountPicked = { index ->
            viewModel.onDestinationAccountPicked(index)
        }
    )
}
