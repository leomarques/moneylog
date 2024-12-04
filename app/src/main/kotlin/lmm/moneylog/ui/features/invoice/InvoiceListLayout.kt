package lmm.moneylog.ui.features.invoice

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
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.topappbar.TopAppBarWithSearch
import lmm.moneylog.ui.features.transaction.list.model.filtered
import lmm.moneylog.ui.features.transaction.list.view.TransactionsListContent
import lmm.moneylog.ui.features.transaction.list.view.transactionModelListPreview

@Composable
fun InvoiceListLayout(
    model: InvoiceListUIState,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier
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
                if (model.transactions.isNotEmpty()) {
                    TransactionsListContent(
                        list = model.transactions.filtered(filter.value),
                        onItemClick = onItemClick
                    )
                } else {
                    EmptyState(
                        stringResource(R.string.empty_transactions_title),
                        stringResource(R.string.empty_invoice_desc)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun InvoiceListLayoutPreview() {
    InvoiceListLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model =
            InvoiceListUIState(
                titleResourceId = R.string.invoice,
                transactions = transactionModelListPreview
            ),
        onItemClick = {}
    )
}
