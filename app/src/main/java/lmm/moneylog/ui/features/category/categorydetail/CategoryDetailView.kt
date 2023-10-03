package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: CategoryDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CategoryDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick()
            onArrowBackClick()
        },
        isEdit = uiState.isEdit,
        valueState = uiState.name,
        onDeleteConfirmClick = {
            viewModel.deleteCategory()
            onArrowBackClick()
        }
    )
}
