package lmm.moneylog.ui.features.transaction.detail.view.components

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.data.account.repositories.model.Account
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.ui.components.bottomsheet.BottomSheetContent
import lmm.moneylog.ui.extensions.toComposeColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransactionDetailDialogs(
    categories: List<Category>,
    accounts: List<Account>,
    showDatePicker: MutableState<Boolean>,
    showAccountPicker: MutableState<Boolean>,
    showCategoryPicker: MutableState<Boolean>,
    showDeleteConfirmDialog: Boolean,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    if (showDatePicker.value) {
        TransactionDetailDatePicker(
            onConfirm = { onDatePicked(it) },
            onDismiss = { showDatePicker.value = false }
        )
    }

    if (showAccountPicker.value) {
        ModalBottomSheet(
            onDismissRequest = { showAccountPicker.value = false }
        ) {
            BottomSheetContent(
                list = accounts.map { it.name to it.color.toComposeColor() },
                onConfirm = { index -> onAccountPicked(index) }
            ) {
                showAccountPicker.value = false
            }
        }
    }

    if (showCategoryPicker.value) {
        ModalBottomSheet(onDismissRequest = { showCategoryPicker.value = false }) {
            BottomSheetContent(
                list = categories.map { it.name to it.color.toComposeColor() },
                onConfirm = { index -> onCategoryPicked(index) }
            ) {
                showCategoryPicker.value = false
            }
        }
    }

    if (showDeleteConfirmDialog) {
        DeleteTransactionConfirmDialog(
            onConfirm = onDeleteConfirm,
            onDismiss = onDeleteDismiss
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TransactionDetailDialogsPreview() {
    TransactionDetailDialogs(
        accounts = listOf(),
        categories = listOf(),
        showDatePicker = mutableStateOf(false),
        showAccountPicker = mutableStateOf(false),
        showCategoryPicker = mutableStateOf(false),
        onDatePicked = {},
        onAccountPicked = {},
        onCategoryPicked = {},
        showDeleteConfirmDialog = false,
        onDeleteConfirm = {},
        onDeleteDismiss = {}
    )
}
