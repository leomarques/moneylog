package lmm.moneylog.ui.features.transaction.transactiondetail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.ConfirmDialog

@Composable
fun DeleteTransactionConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_transaction_delete),
        description = stringResource(R.string.dialog_description_transaction_delete),
        icon = Icons.Default.Delete
    )
}

@Preview
@Composable
fun DeleteTransactionConfirmDialogPreview() {
    DeleteTransactionConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
