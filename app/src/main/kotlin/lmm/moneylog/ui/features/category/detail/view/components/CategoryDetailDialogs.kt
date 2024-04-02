package lmm.moneylog.ui.features.category.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.components.misc.ColorPicker

@Composable
fun CategoryDetailDialogs(
    showDeleteConfirmDialog: Boolean,
    showColorsDialog: MutableState<Boolean>,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onColorPicked: (Color) -> Unit
) {
    if (showDeleteConfirmDialog) {
        DeleteCategoryConfirmDialog(
            onConfirm = onDeleteConfirm,
            onDismiss = onDeleteDismiss
        )
    }

    if (showColorsDialog.value) {
        ColorPicker(
            onConfirm = {
                showColorsDialog.value = false
                onColorPicked(it)
            },
            onDismiss = { showColorsDialog.value = false }
        )
    }
}
