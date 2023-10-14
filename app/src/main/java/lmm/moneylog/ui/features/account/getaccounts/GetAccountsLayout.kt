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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyCircle
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.textformatters.formatForRs
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAccountsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    list: List<AccountModel>,
    onItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit
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
                                painter = painterResource(id = R.drawable.outline_unarchive_24),
                                contentDescription = stringResource(R.string.archive_desc)
                            )
                        }
                    )
                    IconButton(
                        onClick = onTransferIconClick,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_currency_exchange_24),
                                contentDescription = stringResource(R.string.transfer_desc)
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
                    list = list,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun GetAccountsContent(
    list: List<AccountModel>,
    onItemClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        LazyColumn {
            items(list.reversed()) { accountModel ->
                GetAccountsItem(
                    onItemClick = onItemClick,
                    id = accountModel.id,
                    name = accountModel.name,
                    balance = accountModel.balance.formatForRs(),
                    color = accountModel.color
                )
            }
        }
    }
}

@Composable
fun GetAccountsItem(
    onItemClick: (Int) -> Unit,
    id: Int,
    name: String,
    balance: String,
    color: Color
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
            .clickable { onItemClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            MyCircle(color = color)

            Text(
                modifier = Modifier.padding(start = SpaceSize.DefaultSpaceSize),
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
        listOf(
            AccountModel(
                id = 0,
                name = "Itaú",
                balance = 200.0,
                color = DarkRed
            ),
            AccountModel(
                id = 0,
                name = "Itaú",
                balance = 200.0,
                color = DarkRed
            ),
            AccountModel(
                id = 0,
                name = "Itaú",
                balance = 9999999.0,
                color = DarkRed
            )
        ),
        onItemClick = {},
        onArchivedIconClick = {},
        onTransferIconClick = {}
    )
}
