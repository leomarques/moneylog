package lmm.moneylog.ui.features.account.getaccounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAccountsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetAccountsModel,
    onItemClick: (Int) -> Unit
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
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    model = model,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun Content(
    model: GetAccountsModel,
    onItemClick: (Int) -> Unit
) {
    Column(
        Modifier
            .padding(horizontal = SpaceSize.DefaultSpaceSize)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(model.accounts.reversed()) { account ->
                AccountItem(
                    account = account,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun AccountItem(
    account: Account,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceSize.SmallSpaceSize)
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
                color = if (name.isEmpty()) {
                    Color.Gray
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    GetAccountsLayout(
        onArrowBackClick = { },
        onFabClick = { },
        model = GetAccountsModel(
            listOf(
                Account(
                    id = 0,
                    name = "Itaú",
                    color = 0
                ),
                Account(
                    id = 0,
                    name = "Nubank",
                    color = 0
                ),
                Account(
                    id = 0,
                    name = "VR",
                    color = 0
                )
            )
        ),
        onItemClick = {}
    )
}
