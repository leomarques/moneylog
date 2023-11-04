package lmm.moneylog.ui.features.category.list.view.components

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.LazyList
import lmm.moneylog.ui.features.category.list.model.CategoryModel
import lmm.moneylog.ui.features.category.list.view.layouts.CategoriesListItem

@Composable
fun CategoriesTabContent(
    list: List<CategoryModel>,
    onItemClick: (Int) -> Unit
) {
    if (list.isNotEmpty()) {
        LazyList {
            itemsIndexed(list) { index, category ->
                CategoriesListItem(
                    id = category.id,
                    name = category.name,
                    color = category.color,
                    showDivider = index != list.size - 1,
                    onItemClick = onItemClick
                )
            }
        }
    } else {
        EmptyState(
            title = stringResource(R.string.empty_categories_title),
            description = stringResource(R.string.empty_categories_desc)
        )
    }
}

@Preview
@Composable
fun CategoriesTabContentPreview() {
    CategoriesTabContent(
        list = listOf(
            CategoryModel(
                id = 1,
                name = "Category 1",
                color = Color.Gray,
                isIncome = true
            ),
            CategoryModel(
                id = 2,
                name = "Category 2",
                color = Color.Gray,
                isIncome = false
            ),
            CategoryModel(
                id = 3,
                name = "Category 3",
                color = Color.Gray,
                isIncome = true
            )
        ),
        onItemClick = {}
    )
}
