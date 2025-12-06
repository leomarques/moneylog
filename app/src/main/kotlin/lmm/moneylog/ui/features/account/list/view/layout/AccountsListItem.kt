package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.icons.BalanceRoundIcon
import lmm.moneylog.ui.components.misc.ColoredCircle
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
    onItemClick: (Int) -> Unit,
    onAdjustBalanceClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItemContent(
        modifier = modifier,
        id = id,
        onItemClick = onItemClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ColoredCircle(color = color)

                ListItemData(name, balance)

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { onAdjustBalanceClick(id) }
                ) {
                    BalanceRoundIcon()
                }
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
        onItemClick = {},
        onAdjustBalanceClick = {}
    )
}
