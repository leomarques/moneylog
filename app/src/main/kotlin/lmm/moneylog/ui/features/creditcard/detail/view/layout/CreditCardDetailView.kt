package lmm.moneylog.ui.features.creditcard.detail.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.components.misc.showToast
import lmm.moneylog.ui.features.creditcard.detail.viewmodel.CreditCardDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreditCardDetailView(
    viewModel: CreditCardDetailViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val current = LocalContext.current

    CreditCardDetailLayout(
        name = uiState.name,
        color = uiState.color,
        isEdit = uiState.isEdit,
        showFab = uiState.showFab,
        onArrowBackClick = onArrowBackClick,
        onColorPick = { viewModel.onColorPick(it) },
        onNameChange = { viewModel.onNameChange(it) },
        onDeleteConfirmClick = {
            viewModel.deleteCreditCard()
            onArrowBackClick()
        },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { showToast(current, it) }
            )
        },
        closingDay = uiState.closingDay,
        dueDay = uiState.dueDay,
        limit = uiState.limit,
        onClosingDayChange = { viewModel.onClosingDayChange(it) },
        onDueDayDayChange = { viewModel.onDueDayDayChange(it) },
        onLimitChange = { viewModel.onLimitChange(it) }
    )
}
