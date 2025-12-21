package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.HideFab
import lmm.moneylog.ui.features.category.list.model.CategoryModel
import lmm.moneylog.ui.features.category.list.view.components.CategoriesListTopBar
import lmm.moneylog.ui.theme.darkRed

@Composable
fun CategoriesListLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: (Boolean) -> Unit,
    list: List<CategoryModel>,
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTabIsIncome = true

    Scaffold(
        modifier = modifier,
        topBar = { CategoriesListTopBar(onArrowBackClick = onArrowBackClick) },
        floatingActionButton = {
            HideFab(
                onClick = { onFabClick(currentTabIsIncome) },
                icon = Icons.Default.Add
            )
        },
        content = { paddingValues ->
            Box(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("CategoriesListScreen")
            ) {
                CategoriesListContent(
                    list = list,
                    selectedTabIndex = selectedTabIndex,
                    onTabChange = onTabChange,
                    onItemClick = onItemClick,
                    currentTabIsIncome = { currentTabIsIncome = it }
                )
            }
        }
    )
}

@Preview
@Composable
private fun CategoriesListLayoutPreview() {
    CategoriesListLayout(
        onArrowBackClick = { },
        onFabClick = { },
        list =
            listOf(
                CategoryModel(
                    id = 0,
                    name = "Alimentação",
                    color = darkRed,
                    isIncome = true
                ),
                CategoryModel(
                    id = 0,
                    name = "Moradia",
                    color = darkRed,
                    isIncome = true
                ),
                CategoryModel(
                    id = 0,
                    name = "Transporte",
                    color = darkRed,
                    isIncome = false
                )
            ),
        selectedTabIndex = 0,
        onTabChange = { },
        onItemClick = {}
    )
}
