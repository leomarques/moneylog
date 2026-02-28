package lmm.moneylog.ui.features.invoice.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.topappbar.TopAppBarWithSearch
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.transaction.list.view.transactionModelListPreview

@Composable
fun InvoiceListLayout(
    model: InvoiceListUIState,
    onItemClick: (Long) -> Unit,
    onFabClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    onPay: (Int) -> Unit,
    onAdjustClick: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val filter = remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBarWithSearch(
                onSearchTextChange = { filter.value = it },
                onArrowBackClick = onArrowBackClick,
                filter = filter.value,
                titleResourceId = model.titleResourceId
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
        content = { paddingValues ->
            Surface(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("InvoiceListScreen")
            ) {
                InvoiceListContent(
                    model = model,
                    filter = filter.value,
                    onPay = onPay,
                    onAdjustClick = onAdjustClick,
                    onItemClick = onItemClick,
                    onPreviousMonthClick = onPreviousMonthClick,
                    onNextMonthClick = onNextMonthClick
                )
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
                titleResourceId = R.string.common_invoice,
                transactions = transactionModelListPreview,
                totalValue = "R$100.00",
                isInvoicePaid = false,
                cardName = "Nubank",
            ),
        onItemClick = {},
        onFabClick = {},
        onArrowBackClick = {},
        onPay = {},
        onAdjustClick = {},
        onPreviousMonthClick = {},
        onNextMonthClick = {}
    )
}
