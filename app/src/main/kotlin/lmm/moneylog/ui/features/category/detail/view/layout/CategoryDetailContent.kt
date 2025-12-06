package lmm.moneylog.ui.features.category.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.category.detail.view.components.CategoryDetailDialogs
import lmm.moneylog.ui.features.category.detail.view.components.CategoryDetailFields
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CategoryDetailContent(
    name: String,
    color: Color,
    isEdit: Boolean,
    isIncome: Boolean,
    showDeleteConfirmDialog: Boolean,
    onColorPick: (Color) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    onIncomeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Size.DefaultSpaceSize)
            .padding(top = Size.DefaultSpaceSize, bottom = Size.XLargeSpaceSize)
    ) {
        val showColorsDialog = remember { mutableStateOf(false) }

        CategoryDetailDialogs(
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            showColorsDialog = showColorsDialog,
            onColorPick = onColorPick,
            onDeleteConfirm = onDeleteConfirm,
            onDeleteDismiss = onDeleteDismiss
        )

        CategoryDetailFields(
            name = name,
            isEdit = isEdit,
            onNameChange = onNameChange,
            isIncome = isIncome,
            onIncomeChange = onIncomeChange,
            color = color,
            onColorClick = { showColorsDialog.value = true }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryDetailContentPreview() {
    CategoryDetailContent(
        name = "Food",
        color = outcome,
        isEdit = true,
        isIncome = false,
        showDeleteConfirmDialog = false,
        onColorPick = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        onNameChange = {},
        onIncomeChange = {}
    )
}
