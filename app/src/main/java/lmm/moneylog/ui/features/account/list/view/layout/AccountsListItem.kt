package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemData

@Composable
fun AccountsListItem(
    id: Int,
    name: String,
    balance: String,
    color: Color,
    showDivider: Boolean,
    onItemClick: (Int) -> Unit
) {
    ListItemContent(
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

@Preview
@Composable
fun AccountsListItemPreview() {
    AccountsListItem(
        id = 1,
        name = "Account name",
        balance = "R$ 1.000,00",
        color = Color.Red,
        showDivider = true,
        onItemClick = {}
    )
}
