package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.components.misc.ColorPicker

@Composable
fun CreditCardDetailDialogs(
    showDeleteConfirmDialog: Boolean,
    showColorsDialog: Boolean,
    onShowColorsDialogChange: (Boolean) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onColorPick: (Color) -> Unit
) {
    if (showDeleteConfirmDialog) {
        DeleteCreditCardConfirmDialog(
            onConfirm = onDeleteConfirm,
            onDismiss = onDeleteDismiss
        )
    }

    if (showColorsDialog) {
        ColorPicker(
            onConfirm = {
                onShowColorsDialogChange(false)
                onColorPick(it)
            },
            onDismiss = {
                onShowColorsDialogChange(false)
            }
        )
    }
}
