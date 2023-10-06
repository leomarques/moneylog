package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.SearchTopBar
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun GetTransactionsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetTransactionsModel,
    onItemClick: (Int) -> Unit
) {
    val showTopBar = remember { mutableStateOf(true) }
    val filter = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (showTopBar.value) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(model.titleResourceId))
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
                            onClick = { showTopBar.value = false },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.detailtransaction_delete_desc)
                                )
                            }
                        )
                    }
                )
            } else {
                SearchTopBar(
                    searchText = filter.value,
                    placeholderText = stringResource(R.string.search_placeholder),
                    onSearchTextChanged = { filter.value = it },
                    onClearClick = {
                        filter.value = ""
                        showTopBar.value = true
                    },
                    onArrowBackClick = onArrowBackClick
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                List(
                    list = model.transactions,
                    filter = filter,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun List(
    list: List<TransactionModel>,
    filter: MutableState<String>,
    onItemClick: (Int) -> Unit
) {
    val grouped = list
        .filter { transaction ->
            transaction.description
                .startsWith(
                    prefix = filter.value,
                    ignoreCase = true
                )
        }
        .reversed()
        .groupBy { it.date }

    LazyColumn {
        grouped.forEach { (date, transactions) ->
            stickyHeader {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = date,
                        modifier = Modifier
                            .padding(
                                top = SpaceSize.SmallSpaceSize,
                                start = SpaceSize.DefaultSpaceSize
                            ),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = Bold
                    )
                }
            }

            items(transactions) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: TransactionModel,
    onItemClick: (Int) -> Unit
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
            .clickable { onItemClick(transaction.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(transaction) {
            Column(
                Modifier
                    .weight(0.75f)
                    .padding(end = SpaceSize.SmallSpaceSize)
            ) {
                Text(
                    text = description.ifEmpty {
                        stringResource(R.string.gettransactions_nodescription)
                    },
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    color = if (description.isEmpty()) {
                        Color.Gray
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                Text(
                    text = "$category | $account",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Gray
                )
            }

            Text(
                modifier = Modifier.weight(0.25f),
                text = value,
                color =
                if (isIncome) {
                    income
                } else {
                    Color.Red
                },
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    GetTransactionsLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model = GetTransactionsModel(
            listOf(
                TransactionModel(
                    value = "R$50,00",
                    isIncome = true,
                    description = "Calça",
                    date = "1/2/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa"
                ),
                TransactionModel(
                    value = "R$1,00",
                    isIncome = false,
                    description = "",
                    date = "22/12/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa"
                ),
                TransactionModel(
                    value = "R$123456789123123123,00",
                    isIncome = true,
                    description = stringResource(R.string.loremipsum),
                    date = "20/2/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa"
                ),
                TransactionModel(
                    value = "R$123456789123123123,00",
                    isIncome = true,
                    description = "",
                    date = "20/2/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa"
                ),
                TransactionModel(
                    value = "R$1,00",
                    isIncome = true,
                    description = stringResource(R.string.loremipsum),
                    date = "20/2/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa"
                )
            ),
            R.string.gettransactions_topbar_all
        ),
        onItemClick = {}
    )
}
