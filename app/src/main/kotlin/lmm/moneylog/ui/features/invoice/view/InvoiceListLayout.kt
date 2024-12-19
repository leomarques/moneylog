package lmm.moneylog.ui.features.invoice.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.topappbar.TopAppBarWithSearch
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.invoice.view.components.CardInfo
import lmm.moneylog.ui.features.transaction.list.model.filtered
import lmm.moneylog.ui.features.transaction.list.view.TransactionsListContent
import lmm.moneylog.ui.features.transaction.list.view.transactionModelListPreview

@Composable
fun InvoiceListLayout(
    model: InvoiceListUIState,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onPayClick: () -> Unit
) {
    val filter = remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarWithSearch(
                onSearchTextChanged = { filter.value = it },
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
            Surface(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("InvoiceListScreen")
            ) {
                Column {
                    if (model.transactions.isNotEmpty()) {
                        CardInfo(
                            cardName = model.cardName,
                            isInvoicePaid = model.isInvoicePaid,
                            totalValue = model.totalValue,
                            onPayClick = onPayClick
                        )
                    }

                    if (model.transactions.isNotEmpty()) {
                        TransactionsListContent(
                            list = model.transactions.filtered(filter.value),
                            onItemClick = onItemClick,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else {
                        EmptyState(
                            stringResource(R.string.empty_transactions_title),
                            stringResource(R.string.empty_invoice_desc)
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun InvoiceListLayoutPreview() {
    InvoiceListLayout(
        model =
            InvoiceListUIState(
                titleResourceId = R.string.invoice,
                transactions = transactionModelListPreview,
                cardName = "Nubank",
                isInvoicePaid = false,
                totalValue = "R$100.00"
            ),
        onItemClick = {},
        onFabClick = {},
        onArrowBackClick = {},
        onPayClick = {}
    )
}
