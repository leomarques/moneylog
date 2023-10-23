package lmm.moneylog.ui.features.account.accountdetail

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: AccountDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val current = LocalContext.current
    val noNameErrorText = stringResource(R.string.detail_no_name)
    val error = stringResource(R.string.error)

    AccountDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = {
                    onArrowBackClick()
                },
                onError = { stringId ->
                    Toast.makeText(
                        current,
                        when (stringId) {
                            R.string.detail_no_name ->
                                noNameErrorText

                            else -> error
                        },
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        },
        color = uiState.color,
        isEdit = uiState.isEdit,
        valueState = uiState.name,
        onArchiveIconClick = {
            viewModel.archiveAccount()
            onArrowBackClick()
        },
        onColorPicked = {
            viewModel.onColorPicked(it)
        }
    )
}
