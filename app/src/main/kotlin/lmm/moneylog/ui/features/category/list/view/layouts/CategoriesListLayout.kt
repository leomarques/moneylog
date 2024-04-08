package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
    onFabClick: () -> Unit,
    list: List<CategoryModel>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CategoriesListTopBar(
                modifier = modifier,
                onArrowBackClick = onArrowBackClick
            )
        },
        floatingActionButton = {
            HideFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("CategoriesListScreen")
            ) {
                CategoriesListContent(
                    list = list,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Preview
@Composable
fun CategoriesListLayoutPreview() {
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
        onItemClick = {}
    )
}
