package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.components.misc.ColorPicker
import lmm.moneylog.ui.features.account.archive.view.components.ArchiveAccountConfirmDialog

@Composable
fun AccountDetailDialogs(
    showColorsDialog: MutableState<Boolean>,
    onColorPick: (Color) -> Unit,
    showArchiveConfirmDialog: Boolean,
    onArchiveConfirm: () -> Unit,
    onArchiveDismiss: () -> Unit,
    currentColor: Color
) {
    if (showColorsDialog.value) {
        ColorPicker(
            selectedColor = currentColor,
            onConfirm = {
                showColorsDialog.value = false
                onColorPick(it)
            },
            onDismiss = { showColorsDialog.value = false }
        )
    }

    if (showArchiveConfirmDialog) {
        ArchiveAccountConfirmDialog(
            onConfirm = onArchiveConfirm,
            onDismiss = onArchiveDismiss
        )
    }
}
