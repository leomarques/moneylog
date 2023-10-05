package lmm.moneylog.ui.features.transaction.transactiondetail.components

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
import lmm.moneylog.R

@Composable
fun ConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    description: String,
    icon: ImageVector
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.dialog_icon_desc)
            )
        },
        title = {
            Text(
                text = title
            )
        },
        text = {
            Text(
                text = description
            )
        }
    )
}

@Preview
@Composable
fun ConfirmDialogPreview() {
    ConfirmDialog(
        onConfirm = {},
        onDismiss = {},
        title = stringResource(R.string.delete_dialog_transaction_title),
        description = stringResource(R.string.delete_dialog_transaction_description),
        icon = Icons.Default.Delete
    )
}
