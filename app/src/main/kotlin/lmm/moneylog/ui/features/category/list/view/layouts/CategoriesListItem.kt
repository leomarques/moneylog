package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.ColoredCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemName
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CategoriesListItem(
    id: Int,
    name: String,
    color: Color,
    showDivider: Boolean,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItemContent(
        modifier = modifier,
        id = id,
        onItemClick = onItemClick,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ColoredCircle(
                    color = color,
                    letters = name
                )

                ListItemName(
                    modifier = Modifier.padding(start = Size.DefaultSpaceSize),
                    name = name
                )
            }
        }
    )

    if (showDivider) {
        MyDivider()
    }
}

@Preview
@Composable
private fun CategoriesListItemPreview() {
    Surface {
        CategoriesListItem(
            id = 0,
            name = "Category",
            color = outcome,
            showDivider = true,
            onItemClick = {}
        )
    }
}
