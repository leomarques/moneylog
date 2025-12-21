package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import lmm.moneylog.ui.features.category.list.viewmodel.CategoriesListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesListView(
    onArrowBackClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: CategoriesListViewModel = koinViewModel(),
    onFabClick: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CategoriesListLayout(
        list = uiState.list,
        selectedTabIndex = uiState.selectedTabIndex,
        onTabChange = { viewModel.setSelectedTab(it) },
        onArrowBackClick = onArrowBackClick,
        onItemClick = onItemClick,
        onFabClick = onFabClick
    )
}
