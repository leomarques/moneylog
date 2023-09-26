package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailView(
    onArrowBackClick: () -> Unit,
    viewModel: CategoryDetailViewModel = koinViewModel()
) {
    CategoryDetailLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = {
            viewModel.onFabClick()
            onArrowBackClick()
        },
        model = viewModel.model,
        onDeleteConfirmClick = { id ->
            viewModel.deleteCategory(id)
            onArrowBackClick()
        }
    )
}
