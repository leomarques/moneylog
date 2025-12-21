package lmm.moneylog.ui.features.category.list.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun CategoriesTabs(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryTabRow(
        modifier = modifier.padding(bottom = Size.DefaultSpaceSize),
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = { onTabChange(index) }
            )
        }
    }
}

@Preview
@Composable
private fun CategoriesTabPreview() {
    CategoriesTabs(
        selectedTabIndex = 0,
        tabs = listOf("Incomes", "Outcomes"),
        onTabChange = { }
    )
}
