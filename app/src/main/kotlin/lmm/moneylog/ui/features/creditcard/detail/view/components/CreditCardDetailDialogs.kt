package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.components.misc.ColorPicker

@Composable
fun CreditCardDetailDialogs(
    showDeleteConfirmDialog: Boolean,
    showColorsDialog: MutableState<Boolean>,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onColorPicked: (Color) -> Unit
) {
    if (showDeleteConfirmDialog) {
        DeleteCreditCardConfirmDialog(
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
