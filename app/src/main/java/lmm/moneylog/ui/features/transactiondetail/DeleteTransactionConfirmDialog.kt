package lmm.moneylog.ui.features.transactiondetail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteTransactionConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Apagar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete_dialog"
            )
        },
        title = {
            Text(
                text = "Excluir transação?"
            )
        },
        text = {
            Text(
                text = "Isso vai apagar essa transação do seu histórico."
            )
        }
    )
}

@Preview
@Composable
fun MyDeleteConfirmDialogPreview() {
    DeleteTransactionConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
