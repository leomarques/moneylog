package lmm.moneylog.ui.features.transaction.gettransactions

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetTransactionsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetTransactionsModel,
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
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
    model: GetTransactionsModel,
    onItemClick: (Int) -> Unit
) {
    Column(
        Modifier
            .padding(horizontal = SpaceSize.DefaultSpaceSize)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(model.transactions.reversed()) { transaction ->
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
            .fillMaxWidth()
            .padding(vertical = SpaceSize.SmallSpaceSize)
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
                    maxLines = 1,
                    color = if (description.isEmpty()) {
                        Color.Gray
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                Text(
                    text = date,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
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
                    id = 0
                ),
                TransactionModel(
                    value = "R$1,00",
                    isIncome = false,
                    description = "",
                    date = "22/12/2023",
                    id = 0
                ),
                TransactionModel(
                    value = "R$123456789123123123,00",
                    isIncome = true,
                    description = stringResource(R.string.loremipsum),
                    date = "20/2/2023",
                    id = 0
                ),
                TransactionModel(
                    value = "R$123456789123123123,00",
                    isIncome = true,
                    description = "",
                    date = "20/2/2023",
                    id = 0
                ),
                TransactionModel(
                    value = "R$1,00",
                    isIncome = true,
                    description = stringResource(R.string.loremipsum),
                    date = "20/2/2023",
                    id = 0
                )
            ),
            R.string.gettransactions_topbar_all
        ),
        onItemClick = {}
    )
}