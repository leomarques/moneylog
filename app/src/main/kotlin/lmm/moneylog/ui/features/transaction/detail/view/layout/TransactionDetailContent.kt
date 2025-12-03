package lmm.moneylog.ui.features.transaction.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailDialogs
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailFields
import lmm.moneylog.ui.theme.Size

@Composable
fun TransactionDetailContent(
    uiState: TransactionDetailUIState,
    showDeleteConfirmDialog: Boolean,
    onDatePick: (Long) -> Unit,
    onAccountPick: (Int) -> Unit,
    onCategoryPick: (Int) -> Unit,
    onCreditCardPick: (Int) -> Unit,
    onInvoicePick: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onIsIncomeSelect: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSuggestionClick: (TransactionSuggestion) -> Unit,
    onDebtSelect: () -> Unit,
    onCreditSelect: () -> Unit,
    modifier: Modifier = Modifier
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
            onDatePick = onDatePick,
            onAccountPick = onAccountPick,
            onCategoryPick = onCategoryPick,
            onCreditCardPick = onCreditCardPick,
            onInvoicePick = onInvoicePick,
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
            onSuggestionClick = onSuggestionClick,
            isCategoriesFieldEnabled = filteredCategories.isNotEmpty(),
            isAccountsFieldEnabled = uiState.accounts.isNotEmpty(),
            isCreditCardFieldEnabled = uiState.creditCards.isNotEmpty(),
            onDateClick = { showDatePicker.value = true },
            onCreditCardClick = { showCreditCardPicker.value = true },
            onInvoiceClick = { showInvoicePicker.value = true },
            onDebtSelect = onDebtSelect,
            onCreditSelect = onCreditSelect,
            isDebtSelected = uiState.isDebtSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailContentPreview() {
    TransactionDetailContent(
        uiState = TransactionDetailUIState(),
        showDeleteConfirmDialog = false,
        onDatePick = {},
        onAccountPick = {},
        onCategoryPick = {},
        onCreditCardPick = {},
        onInvoicePick = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        onIsIncomeSelect = {},
        onValueChange = {},
        onDescriptionChange = {},
        onSuggestionClick = {},
        onDebtSelect = {},
        onCreditSelect = {}
    )
}
