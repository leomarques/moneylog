package lmm.moneylog.ui.features.category.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
    name: String,
    color: Color,
    isEdit: Boolean,
    isIncome: Boolean,
    showDeleteConfirmDialog: Boolean,
    onColorPicked: (Color) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    onIncomeChange: (Boolean) -> Unit
) {
    Column(Modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showColorsDialog = remember { mutableStateOf(false) }

        CategoryDetailDialogs(
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            showColorsDialog = showColorsDialog,
            onColorPicked = onColorPicked,
            onDeleteConfirm = onDeleteConfirm,
            onDeleteDismiss = onDeleteDismiss
        )

        CategoryDetailFields(
            name = name,
            color = color,
            isEdit = isEdit,
            isIncome = isIncome,
            showColorsDialog = showColorsDialog,
            onNameChange = onNameChange,
            onIncomeChange = onIncomeChange
        )
    }
}

@Preview
@Composable
fun CategoryDetailContentPreview() {
    CategoryDetailContent(
        name = "Food",
        color = outcome,
        isEdit = true,
        isIncome = false,
        showDeleteConfirmDialog = false,
        onColorPicked = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        onNameChange = {},
        onIncomeChange = {}
    )
}
