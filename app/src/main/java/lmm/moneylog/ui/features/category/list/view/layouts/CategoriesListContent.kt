package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.features.category.list.model.CategoryModel
import lmm.moneylog.ui.theme.Size

@Composable
fun CategoriesListContent(
    onItemClick: (Int) -> Unit,
    list: List<CategoryModel>
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.incomes),
        stringResource(id = R.string.outcomes)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        val filteredList = list.filter { it.isIncome == (tabIndex == 0) }

        if (filteredList.isNotEmpty()) {
            LazyColumn(
                Modifier.background(
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    shape = RoundedCornerShape(20.dp)
                )
            ) {
                itemsIndexed(filteredList.reversed()) { index, category ->
                    CategoriesListItem(
                        onItemClick = onItemClick,
                        id = category.id,
                        name = category.name,
                        color = category.color,
                        showDivider = index != filteredList.size - 1
                    )
                }
            }
        } else {
            EmptyState(
                title = stringResource(id = R.string.empty_categories_title),
                description = stringResource(id = R.string.empty_categories_desc)
            )
        }
    }
}
