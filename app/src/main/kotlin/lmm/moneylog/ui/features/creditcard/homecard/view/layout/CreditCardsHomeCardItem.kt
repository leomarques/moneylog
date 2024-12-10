package lmm.moneylog.ui.features.creditcard.homecard.view.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.ColoredCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemName
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CreditCardsHomeCardItem(
    id: Int,
    name: String,
    color: Color,
    showDivider: Boolean,
    onItemClick: (Int) -> Unit,
    hideValues: Boolean,
    value: Double,
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

                Column(
                    modifier = Modifier.padding(start = Size.DefaultSpaceSize),
                    verticalArrangement = Arrangement.Center
                ) {
                    ListItemName(name = name)

                    if (hideValues) {
                        Text("****")
                    } else {
                        Text(
                            text = value.formatForRs(),
                            color = outcome
                        )
                    }
                }
            }
        }
    )

    if (showDivider) {
        MyDivider(color = MaterialTheme.colorScheme.onTertiaryContainer)
    }
}

@Preview
@Composable
private fun CreditCardsListItemPreview() {
    Surface {
        CreditCardsHomeCardItem(
            id = 0,
            name = "Card",
            color = outcome,
            showDivider = true,
            onItemClick = {},
            hideValues = false,
            value = 100.0
        )
    }
}
