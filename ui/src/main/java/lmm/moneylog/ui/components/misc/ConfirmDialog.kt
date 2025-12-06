package lmm.moneylog.ui.components.misc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

@Composable
fun ConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    description: String,
    icon: ImageVector
) {
    AlertDialog(
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.ui_action_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.ui_action_cancel))
            }
        },
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.ui_cd_dialog)
            )
        },
        title = { Text(text = title) },
        text = { Text(text = description) }
    )
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    ConfirmDialog(
        onConfirm = {},
        onDismiss = {},
        title = stringResource(R.string.ui_dialog_transaction_delete_title),
        description = stringResource(R.string.ui_dialog_transaction_delete_desc),
        icon = Icons.Default.Delete
    )
}
