package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

@Composable
fun ListItemData(
    name: String,
    balance: String,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(start = Size.DefaultSpaceSize)) {
        ListItemName(name = name)

        ListItemBalance(
            balance = balance,
            color =
                if (balance.contains("-")) {
                    outcome
                } else {
                    income
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemDataPreview() {
    ListItemData(
        name = "Account name",
        balance = "R$ 1.000,00"
    )
}
