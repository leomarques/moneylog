package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: AccountDetailViewModel = koinViewModel()
) {
    AccountDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick()
            onArrowBackClick()
        },
        model = viewModel.model,
        onDeleteConfirmClick = { id ->
            viewModel.deleteAccount(id)
            onArrowBackClick()
        }
    )
}
