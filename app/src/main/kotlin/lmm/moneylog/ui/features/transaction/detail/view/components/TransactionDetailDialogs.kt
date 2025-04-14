package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.ui.components.bottomsheet.BottomSheetContent
import lmm.moneylog.ui.extensions.toComposeColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransactionDetailDialogs(
    categories: List<Category>,
    accounts: List<Account>,
    creditCards: List<CreditCard>,
    invoices: List<Invoice>,
    showDatePicker: MutableState<Boolean>,
    showAccountPicker: MutableState<Boolean>,
    showCategoryPicker: MutableState<Boolean>,
    showCreditCardPicker: MutableState<Boolean>,
    showInvoicePicker: MutableState<Boolean>,
    showDeleteConfirmDialog: Boolean,
    onDatePick: (Long) -> Unit,
    onAccountPick: (Int) -> Unit,
    onCategoryPick: (Int) -> Unit,
    onCreditCardPick: (Int) -> Unit,
    onInvoicePick: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    if (showDatePicker.value) {
        TransactionDetailDatePicker(
            onConfirm = { onDatePick(it) },
            onDismiss = { showDatePicker.value = false }
        )
    }

    if (showAccountPicker.value) {
        ModalBottomSheet(
            onDismissRequest = { showAccountPicker.value = false }
        ) {
            BottomSheetContent(
                list = accounts.map { it.name to it.color.toComposeColor() },
                onConfirm = { index -> onAccountPick(index) },
                onDismiss = {
                    showAccountPicker.value = false
                },
            )
        }
    }

    if (showCategoryPicker.value) {
        ModalBottomSheet(onDismissRequest = { showCategoryPicker.value = false }) {
            BottomSheetContent(
                list = categories.map { it.name to it.color.toComposeColor() },
                onConfirm = { index -> onCategoryPick(index) },
                onDismiss = {
                    showCategoryPicker.value = false
                },
            )
        }
    }

    if (showCreditCardPicker.value) {
        ModalBottomSheet(onDismissRequest = { showCreditCardPicker.value = false }) {
            BottomSheetContent(
                list = creditCards.map { it.name to it.color.toComposeColor() },
                onConfirm = { index -> onCreditCardPick(index) },
                onDismiss = {
                    showCreditCardPicker.value = false
                },
            )
        }
    }

    if (showInvoicePicker.value) {
        ModalBottomSheet(onDismissRequest = { showInvoicePicker.value = false }) {
            BottomSheetContent(
                list = invoices.map { it.name to null },
                onConfirm = { index -> onInvoicePick(index) },
                onDismiss = {
                    showInvoicePicker.value = false
                },
            )
        }
    }

    if (showDeleteConfirmDialog) {
        DeleteTransactionConfirmDialog(
            onConfirm = onDeleteConfirm,
            onDismiss = onDeleteDismiss
        )
    }
}

@Preview
@Composable
private fun TransactionDetailDialogsPreview() {
    TransactionDetailDialogs(
        categories = listOf(),
        accounts = listOf(),
        showDatePicker = remember { mutableStateOf(false) },
        showAccountPicker = remember { mutableStateOf(false) },
        showCategoryPicker = remember { mutableStateOf(false) },
        showCreditCardPicker = remember { mutableStateOf(false) },
        showInvoicePicker = remember { mutableStateOf(false) },
        showDeleteConfirmDialog = false,
        onDatePick = {},
        onAccountPick = {},
        onCategoryPick = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        creditCards = listOf(),
        invoices = listOf(),
        onCreditCardPick = {},
        onInvoicePick = {}
    )
}
