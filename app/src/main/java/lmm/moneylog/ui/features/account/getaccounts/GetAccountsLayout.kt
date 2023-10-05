package lmm.moneylog.ui.features.account.getaccounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.data.account.Account
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAccountsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetAccountsModel,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.getaccounts_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onArchivedIconClick,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = stringResource(R.string.archive_desc)
                            )
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                GetAccountsContent(
                    model = model,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun GetAccountsContent(
    model: GetAccountsModel,
    onItemClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        LazyColumn {
            items(model.list.reversed()) { accountModel ->
                GetAccountsItem(
                    account = accountModel.account,
                    onItemClick = onItemClick,
                    balance = accountModel.balance
                )
            }
        }
    }
}

@Composable
fun GetAccountsItem(
    account: Account,
    onItemClick: (Int) -> Unit,
    balance: String
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(SpaceSize.ListItemHeight)
            .padding(
                vertical = SpaceSize.SmallSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(account.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(account) {
            Text(
                text = name.ifEmpty {
                    stringResource(R.string.gettransactions_nodescription)
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                color = if (name.isEmpty()) {
                    Color.Gray
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }

        Text(
            text = balance,
            color = if (balance.contains("-")) Color.Red else income
        )
    }

    Divider()
}

@Preview
@Composable
fun GetAccountsLayoutPreview() {
    GetAccountsLayout(
        onArrowBackClick = { },
        onFabClick = { },
        model = GetAccountsModel(
            listOf(
                AccountModel(
                    account = Account(
                        id = 0,
                        name = "Itaú",
                        color = 0,
                        archived = false
                    ),
                    balance = "R$200,00"
                ),
                AccountModel(
                    account = Account(
                        id = 0,
                        name = "Itaú",
                        color = 0,
                        archived = false
                    ),
                    balance = "R$-200,00"
                ),
                AccountModel(
                    account = Account(
                        id = 0,
                        name = "Itaú",
                        color = 0,
                        archived = false
                    ),
                    balance = "R$99999,00"
                )
            )
        ),
        onItemClick = {},
        onArchivedIconClick = {}
    )
}
