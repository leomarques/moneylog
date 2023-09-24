package lmm.moneylog.ui.features.category.getccategories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetCategoriesView(
    viewModel: GetCategoriesViewModel = koinViewModel(),
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val model by viewModel.uiState.collectAsState()

    GetCategoriesLayout(
        onArrowBackClick = onArrowBackClick,
        onFabClick = onFabClick,
        model = model,
        onItemClick = onItemClick
    )
}
