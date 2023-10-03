package lmm.moneylog.ui.features.transaction.transactiondetail.components.deleteconfirmdialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DeleteConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    deleteDialogTitle: String,
    deleteDialogDescription: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.delete_dialog_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_dialog_icon_desc)
            )
        },
        title = {
            Text(
                text = deleteDialogTitle
            )
        },
        text = {
            Text(
                text = deleteDialogDescription
            )
        }
    )
}

@Preview
@Composable
fun DeleteConfirmDialogPreview() {
    DeleteConfirmDialog(
        onConfirm = {},
        onDismiss = {},
        deleteDialogTitle = stringResource(R.string.delete_dialog_transaction_title),
        deleteDialogDescription = stringResource(R.string.delete_dialog_transaction_description)
    )
}
