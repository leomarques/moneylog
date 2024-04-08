package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemData
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountsListItem(
    id: Int,
    name: String,
    balance: String,
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
            Row {
                MyCircle(color = color)

                ListItemData(name, balance)
            }
        }
    )

    if (showDivider) {
        MyDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountsListItemPreview() {
    AccountsListItem(
        id = 1,
        name = "Account name",
        balance = "R$ 1.000,00",
        color = outcome,
        showDivider = true,
        onItemClick = {}
    )
}
