package lmm.moneylog.ui.features.transaction.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailDialogs
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailFields
import lmm.moneylog.ui.theme.Size

@Composable
fun TransactionDetailContent(
    uiState: TransactionDetailUIState,
    showDeleteConfirmDialog: Boolean,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit,
    onCreditCardPicked: (Int) -> Unit,
    onInvoicePicked: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onIsIncomeSelect: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {
    Column(modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showDatePicker = remember { mutableStateOf(false) }
        val showAccountPicker = remember { mutableStateOf(false) }
        val showCategoryPicker = remember { mutableStateOf(false) }
        val showCreditCardPicker = remember { mutableStateOf(false) }
        val showInvoicePicker = remember { mutableStateOf(false) }

        val filteredCategories =
            uiState.categories.filter { it.isIncome == uiState.isIncome }

        TransactionDetailDialogs(
            accounts = uiState.accounts,
            categories = filteredCategories,
            creditCards = uiState.creditCards,
            invoices = uiState.invoices,
            showDatePicker = showDatePicker,
            showAccountPicker = showAccountPicker,
            showCategoryPicker = showCategoryPicker,
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            showCreditCardPicker = showCreditCardPicker,
            showInvoicePicker = showInvoicePicker,
            onDatePicked = onDatePicked,
            onAccountPicked = onAccountPicked,
            onCategoryPicked = onCategoryPicked,
            onCreditCardPicked = onCreditCardPicked,
            onInvoicePicked = onInvoicePicked,
            onDeleteConfirm = onDeleteConfirm,
            onDeleteDismiss = onDeleteDismiss
        )

        TransactionDetailFields(
            uiState = uiState,
            onIsIncomeSelect = onIsIncomeSelect,
            onAccountClick = { showAccountPicker.value = true },
            onCategoryClick = { showCategoryPicker.value = true },
            onValueChange = onValueChange,
            onDescriptionChange = onDescriptionChange,
            isCategoriesFieldEnabled = filteredCategories.isNotEmpty(),
            isAccountsFieldEnabled = uiState.accounts.isNotEmpty(),
            onDateClick = { showDatePicker.value = true },
            onCreditCardClick = { showCreditCardPicker.value = true },
            onInvoiceClick = { showInvoicePicker.value = true }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailContentPreview() {
    TransactionDetailContent(
        uiState = TransactionDetailUIState(),
        showDeleteConfirmDialog = false,
        onDatePicked = {},
        onAccountPicked = {},
        onCategoryPicked = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        onIsIncomeSelect = {},
        onValueChange = {},
        onDescriptionChange = {},
        onCreditCardPicked = {},
        onInvoicePicked = {}
    )
}
