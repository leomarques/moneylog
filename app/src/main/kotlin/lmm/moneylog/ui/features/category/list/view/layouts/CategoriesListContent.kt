package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
    modifier: Modifier = Modifier
) {
    val tabIndex = remember { mutableIntStateOf(0) }
    val tabs =
        listOf(
            stringResource(id = R.string.incomes),
            stringResource(id = R.string.outcomes)
        )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoriesTabs(
            tabIndex = tabIndex,
            tabs = tabs
        )

        CategoriesTabContent(
            list = list.filter { it.isIncome == (tabIndex.intValue == 0) },
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
            )
    )
}
