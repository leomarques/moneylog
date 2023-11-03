package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.list.view.components.ListItemBalance
import lmm.moneylog.ui.features.account.list.view.components.ListItemContent
import lmm.moneylog.ui.features.account.list.view.components.ListItemName
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

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

                Column(Modifier.padding(start = Size.DefaultSpaceSize)) {
                    ListItemName(name)

                    ListItemBalance(
                        balance = balance,
                        color = if (balance.contains("-")) {
                            outcome
                        } else {
                            income
                        }
                    )
                }
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
