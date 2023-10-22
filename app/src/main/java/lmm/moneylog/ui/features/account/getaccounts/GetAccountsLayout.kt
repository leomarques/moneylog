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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.EmptyState
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
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.getaccounts_topbar)) },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.detailtransaction_arrowback_desc
                            )
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
            if (showFab) {
                MyFab(
                    onClick = {
                        showFab = false
                        onFabClick()
                    },
                    icon = Icons.Default.Add
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                if (list.isNotEmpty()) {
                    GetAccountsList(
                        list = list,
                        onItemClick = onItemClick
                    )
                } else {
                    EmptyState(
                        stringResource(R.string.empty_accounts_title),
                        stringResource(R.string.empty_accounts_desc)
                    )
                }
            }
        }
    )
}

@Composable
fun GetAccountsList(
    list: List<AccountModel>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        Modifier.background(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            shape = RoundedCornerShape(20.dp)
        )
    ) {
        itemsIndexed(list.reversed()) { index, accountModel ->
            GetAccountsItem(
                onItemClick = onItemClick,
                id = accountModel.id,
                name = accountModel.name,
                balance = accountModel.balance.formatForRs(),
                color = accountModel.color,
                showDivider = index != list.size - 1
            )
        }
    }
}

@Composable
fun GetAccountsItem(
    onItemClick: (Int) -> Unit,
    id: Int,
    name: String,
    balance: String,
    color: Color,
    showDivider: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpaceSize.TwoLinesListItemHeight)
            .padding(
                vertical = SpaceSize.DefaultSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            MyCircle(color = color)

            Column(Modifier.padding(start = SpaceSize.DefaultSpaceSize)) {
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

                Row {
                    Text(
                        text = stringResource(R.string.current_balance),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = balance,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (balance.contains("-")) Color.Red else income
                    )
                }
            }
        }
    }

    if (showDivider) {
        Divider(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize))
    }
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
