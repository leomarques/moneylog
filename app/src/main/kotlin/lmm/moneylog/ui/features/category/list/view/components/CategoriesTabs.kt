package lmm.moneylog.ui.features.category.list.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun CategoriesTabs(
    tabIndex: MutableState<Int>,
    tabs: List<String>,
    modifier: Modifier = Modifier
) {
    PrimaryTabRow(
        modifier = modifier.padding(bottom = Size.DefaultSpaceSize),
        selectedTabIndex = tabIndex.value
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = tabIndex.value == index,
                onClick = { tabIndex.value = index }
            )
        }
    }
}

@Preview
@Composable
private fun CategoriesTabPreview() {
    CategoriesTabs(
        tabIndex = remember { mutableIntStateOf(0) },
        tabs = listOf("Incomes", "Outcomes")
    )
}
