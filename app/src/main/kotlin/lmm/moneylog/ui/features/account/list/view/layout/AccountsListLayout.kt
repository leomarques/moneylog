package lmm.moneylog.ui.features.account.list.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.HideFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.features.account.list.model.AccountModel
import lmm.moneylog.ui.features.account.list.view.components.AccountsListTopBar
import lmm.moneylog.ui.theme.darkRed

@Composable
fun AccountsListLayout(
    list: List<AccountModel>,
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    onTransferIconClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AccountsListTopBar(
                onArrowBackClick = onArrowBackClick,
                onArchivedIconClick = onArchivedIconClick,
                onTransferIconClick = onTransferIconClick
            )
        },
        floatingActionButton = {
            HideFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("AccountsListScreen")
            ) {
                if (list.isNotEmpty()) {
                    AccountsListContent(
                        list = list,
                        onItemClick = onItemClick
                    )
                } else {
                    EmptyState(
                        title = stringResource(R.string.empty_accounts_title),
                        description = stringResource(R.string.empty_accounts_desc)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AccountsListLayoutPreview() {
    AccountsListLayout(
        list =
            listOf(
                AccountModel(
                    id = 0,
                    name = "Itaú",
                    balance = "R$200.0",
                    color = darkRed
                ),
                AccountModel(
                    id = 0,
                    name = "Itaú",
                    balance = "R$200.0",
                    color = darkRed
                ),
                AccountModel(
                    id = 0,
                    name = "Itaú",
                    balance = "R$200.0",
                    color = darkRed
                )
            ),
        onArrowBackClick = { },
        onFabClick = { },
        onItemClick = {},
        onArchivedIconClick = {},
        onTransferIconClick = {}
    )
}
