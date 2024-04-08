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
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onIsIncomeSelect: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {
    Column(Modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showDatePicker = remember { mutableStateOf(false) }
        val showAccountPicker = remember { mutableStateOf(false) }
        val showCategoryPicker = remember { mutableStateOf(false) }

        val filteredCategories =
            uiState.categories.filter { it.isIncome == uiState.isIncome }

        TransactionDetailDialogs(
            accounts = uiState.accounts,
            categories = filteredCategories,
            showDatePicker = showDatePicker,
            showAccountPicker = showAccountPicker,
            showCategoryPicker = showCategoryPicker,
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            onDatePicked = onDatePicked,
            onAccountPicked = onAccountPicked,
            onCategoryPicked = onCategoryPicked,
            onDeleteConfirm = onDeleteConfirm,
            onDeleteDismiss = onDeleteDismiss
        )

        TransactionDetailFields(
            uiState = uiState,
            onValueChange = onValueChange,
            onDescriptionChange = onDescriptionChange,
            onIsIncomeSelect = onIsIncomeSelect,
            onDateClick = { showDatePicker.value = true },
            onAccountClick = { showAccountPicker.value = true },
            onCategoryClick = { showCategoryPicker.value = true },
            uisAccountsFieldEnabled = uiState.accounts.isNotEmpty(),
            isCategoriesFieldEnabled = filteredCategories.isNotEmpty()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailContentPreview() {
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
        onDescriptionChange = {}
    )
}
