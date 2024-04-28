package lmm.moneylog.ui.features.creditcard.list.view.layouts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemName
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CreditCardsListItem(
    id: Int,
    name: String,
    color: Color,
    showDivider: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    ListItemContent(
        modifier = modifier,
        id = id,
        onItemClick = onItemClick,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MyCircle(
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
private fun CreditCardsListItemPreview() {
    Surface {
        CreditCardsListItem(
            id = 0,
            name = "Card",
            color = outcome,
            showDivider = true,
            onItemClick = {}
        )
    }
}
