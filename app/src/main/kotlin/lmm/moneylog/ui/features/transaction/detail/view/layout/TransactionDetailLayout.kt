package lmm.moneylog.ui.features.transaction.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    uiState: TransactionDetailUIState,
    onArrowBackClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onFabClick: () -> Unit,
    onDatePick: (Long) -> Unit,
    onAccountPick: (Int) -> Unit,
    onCategoryPick: (Int) -> Unit,
    onCreditCardPick: (Int) -> Unit,
    onInvoicePick: (Int) -> Unit,
    onIsIncomeSelect: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSuggestionClick: (TransactionSuggestion) -> Unit,
    onDebtSelect: () -> Unit,
    onCreditSelect: () -> Unit,
    onClearSuggestions: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TransactionDetailTopBar(
                onArrowBackClick = onArrowBackClick,
                isEdit = uiState.isEdit,
                onDeleteClick = { showDeleteConfirmDialog.value = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (uiState.showFab) {
                MyFab(
                    onClick = onFabClick,
                    icon = Icons.Default.Check
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                TransactionDetailContent(
                    uiState = uiState,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDatePick = onDatePick,
                    onAccountPick = onAccountPick,
                    onCategoryPick = onCategoryPick,
                    onCreditCardPick = onCreditCardPick,
                    onInvoicePick = onInvoicePick,
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    },
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onIsIncomeSelect = onIsIncomeSelect,
                    onValueChange = onValueChange,
                    onDescriptionChange = onDescriptionChange,
                    onSuggestionClick = onSuggestionClick,
                    onDebtSelect = onDebtSelect,
                    onCreditSelect = onCreditSelect,
                    onClearSuggestions = onClearSuggestions
                )
            }
        }
    )
}

@Preview
@Composable
private fun TransactionDetailLayoutPreview() {
    TransactionDetailLayout(
        uiState = TransactionDetailUIState(),
        onArrowBackClick = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onDatePick = {},
        onAccountPick = {},
        onCategoryPick = {},
        onCreditCardPick = {},
        onInvoicePick = {},
        onIsIncomeSelect = {},
        onValueChange = {},
        onDescriptionChange = {},
        onSuggestionClick = {},
        onDebtSelect = {},
        onCreditSelect = {},
        onClearSuggestions = {}
    )
}
