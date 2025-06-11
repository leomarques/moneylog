package lmm.moneylog.ui.features.transaction.list.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.MonthSelector
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.topappbar.TopAppBarWithSearch
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.transaction.list.model.TransactionsListUIState
import lmm.moneylog.ui.features.transaction.list.model.filtered
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

@Composable
fun TransactionsListLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    model: TransactionsListUIState,
    modifier: Modifier = Modifier,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit
) {
    val filter = remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarWithSearch(
                onSearchTextChange = { filter.value = it },
                onArrowBackClick = onArrowBackClick,
                filter = filter.value,
                titleResourceId = model.titleResourceId
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
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
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("TransactionsListScreen")
            ) {
                MonthSelector(
                    onPreviousMonthClick = onPreviousMonthClick,
                    onNextMonthClick = onNextMonthClick,
                    monthName = model.monthName,
                    modifier = Modifier.fillMaxWidth()
                )

                TotalValue(model, modifier)

                List(
                    model = model,
                    filter = filter,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun TotalValue(
    model: TransactionsListUIState,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Total: ${model.total.formatForRs()}",
            color = when {
                model.total == 0.0 -> White
                model.total < 0.0 -> outcome
                else -> income
            }
        )
    }
}

@Composable
private fun List(
    model: TransactionsListUIState,
    filter: MutableState<String>,
    onItemClick: (Int) -> Unit
) {
    if (model.transactions.isNotEmpty()) {
        TransactionsListContent(
            list = model.transactions.filtered(filter.value),
            onItemClick = onItemClick
        )
    } else {
        EmptyState(
            stringResource(R.string.empty_transactions_title),
            stringResource(R.string.empty_transactions_desc)
        )
    }
}

@Preview
@Composable
private fun TransactionsListLayoutPreview() {
    TransactionsListLayout(
        onArrowBackClick = {},
        onFabClick = {},
        onItemClick = {},
        model =
            TransactionsListUIState(
                titleResourceId = R.string.transactions,
                monthName = "January",
                transactions = transactionModelListPreview,
                total = 500.0
            ),
        onPreviousMonthClick = {},
        onNextMonthClick = {}
    )
}
