package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.misc.LazyList
import lmm.moneylog.ui.features.account.list.model.AccountModel
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun AccountsListContent(
    list: List<AccountModel>,
    onItemClick: (Int) -> Unit
) {
    LazyList {
        itemsIndexed(list) { index, accountModel ->
            with(accountModel) {
                AccountsListItem(
                    id = id,
                    name = name,
                    balance = balance,
                    color = color,
                    showDivider = index != list.size - 1,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun AccountsListContentPreview() {
    AccountsListContent(
        list =
            listOf(
                AccountModel(
                    id = 0,
                    name = "Bank",
                    balance = "R$1000.0",
                    color = neutralColor
                ),
                AccountModel(
                    id = 1,
                    name = "Cash",
                    balance = "R$100.0",
                    color = neutralColor
                )
            ),
        onItemClick = {}
    )
}
