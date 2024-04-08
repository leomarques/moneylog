package lmm.moneylog.ui.features.transaction.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.features.transaction.detail.view.components.TransactionDetailTopBar

@Composable
fun TransactionDetailLayout(
    uiState: TransactionDetailUIState,
    onArrowBackClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onFabClick: () -> Unit,
    onDatePick: (Long) -> Unit,
    onAccountPick: (Int) -> Unit,
    onCategoryPick: (Int) -> Unit,
    onIsIncomeSelect: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TransactionDetailTopBar(
                onArrowBackClick = onArrowBackClick,
                isEdit = uiState.isEdit,
                onDeleteClick = { showDeleteConfirmDialog.value = true }
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
                    onDatePicked = onDatePick,
                    onAccountPicked = onAccountPick,
                    onCategoryPicked = onCategoryPick,
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    },
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onIsIncomeSelect = onIsIncomeSelect,
                    onValueChange = onValueChange,
                    onDescriptionChange = onDescriptionChange
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
        onIsIncomeSelect = {},
        onValueChange = {},
        onDescriptionChange = {}
    )
}
