package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.features.category.list.model.CategoryModel
import lmm.moneylog.ui.features.category.list.view.components.CategoriesTabContent
import lmm.moneylog.ui.features.category.list.view.components.CategoriesTabs
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun CategoriesListContent(
    onItemClick: (Int) -> Unit,
    list: List<CategoryModel>,
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit,
    currentTabIsIncome: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs =
        listOf(
            stringResource(id = R.string.common_incomes),
            stringResource(id = R.string.common_outcomes)
        )

    // Notify parent of current tab state
    val isIncome = selectedTabIndex == 0
    currentTabIsIncome(isIncome)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoriesTabs(
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onTabChange = onTabChange
        )

        CategoriesTabContent(
            list = list.filter { it.isIncome == isIncome },
            onItemClick = onItemClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesListContentPreview() {
    CategoriesListContent(
        onItemClick = { },
        list =
            listOf(
                CategoryModel(
                    id = 1,
                    name = "Category 1",
                    color = neutralColor,
                    isIncome = true
                ),
                CategoryModel(
                    id = 2,
                    name = "Category 2",
                    color = neutralColor,
                    isIncome = false
                ),
                CategoryModel(
                    id = 3,
                    name = "Category 3",
                    color = neutralColor,
                    isIncome = true
                )
            ),
        selectedTabIndex = 0,
        onTabChange = { },
        currentTabIsIncome = { }
    )
}
