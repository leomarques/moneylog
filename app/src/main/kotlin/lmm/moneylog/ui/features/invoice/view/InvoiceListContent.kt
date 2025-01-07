package lmm.moneylog.ui.features.invoice.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.ui.components.bottomsheet.BottomSheetContent
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.LoadingDialog
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.invoice.view.components.CardInfo
import lmm.moneylog.ui.features.transaction.detail.view.components.PayInvoiceConfirmDialog
import lmm.moneylog.ui.features.transaction.list.model.filtered
import lmm.moneylog.ui.features.transaction.list.view.TransactionsListContent
import lmm.moneylog.ui.features.transaction.list.view.transactionModelListPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceListContent(
    model: InvoiceListUIState,
    filter: String,
    onPay: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        val showAccountPicker = remember { mutableStateOf(false) }
        val showPayConfirmDialog = remember { mutableStateOf(false) }
        val accountToPay = remember { mutableStateOf<Account?>(null) }

        if (showAccountPicker.value) {
            ModalBottomSheet(
                onDismissRequest = { showAccountPicker.value = false }
            ) {
                BottomSheetContent(
                    list = model.accounts.map { it.name to it.color.toComposeColor() },
                    text = stringResource(R.string.select_account),
                    onConfirm = { index ->
                        accountToPay.value = model.accounts[index]
                        showAccountPicker.value = false
                        showPayConfirmDialog.value = true
                    },
                    onDismiss = {
                        showAccountPicker.value = false
                    },
                )
            }
        }

        if (model.isLoadingWhilePay) {
            LoadingDialog()
        }

        if (showPayConfirmDialog.value) {
            PayInvoiceConfirmDialog(
                value = model.totalValue,
                account = accountToPay.value?.name ?: "",
                onConfirm = {
                    accountToPay.value?.let { onPay(it.id) }
                    showPayConfirmDialog.value = false
                },
                onDismiss = {
                    showPayConfirmDialog.value = false
                }
            )
        }

        CardInfo(
            cardName = model.cardName,
            isInvoicePaid = model.isInvoicePaid,
            totalValue = model.totalValue,
            monthName = model.monthName,
            onPayClick = {
                showAccountPicker.value = true
            },
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick
        )

        if (model.transactions.isNotEmpty()) {
            TransactionsListContent(
                list = model.transactions.filtered(filter),
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

@Preview
@Composable
private fun InvoiceListContentPreview() {
    InvoiceListContent(
        model =
            InvoiceListUIState(
                titleResourceId = 0,
                transactions = transactionModelListPreview,
                totalValue = "R$100.00",
                isInvoicePaid = false,
                cardName = "Card",
            ),
        filter = "",
        onPay = {},
        onItemClick = {},
        onPreviousMonthClick = {},
        onNextMonthClick = {}
    )
}
