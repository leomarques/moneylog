package lmm.moneylog.ui.features.category.detail.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.ui.features.category.detail.viewmodel.CategoryDetailViewModel
import lmm.moneylog.ui.features.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: CategoryDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val current = LocalContext.current

    CategoryDetailLayout(
        name = uiState.name,
        color = uiState.color,
        isEdit = uiState.isEdit,
        isIncome = uiState.isIncome,
        showFab = uiState.showFab,
        onArrowBackClick = onArrowBackClick,
        onColorPicked = { viewModel.onColorPick(it) },
        onNameChange = { viewModel.onNameChange(it) },
        onIncomeChange = { viewModel.onIncomeChange(it) },
        onDeleteConfirmClick = {
            viewModel.deleteCategory()
            onArrowBackClick()
        },
        onFabClick = {
            viewModel.onFabClick(
                onSuccess = onArrowBackClick,
                onError = { showToast(current, it) }
            )
        }
    )
}
