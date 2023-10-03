package lmm.moneylog.ui.features.transaction.transactiondetail.components.deleteconfirmdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DeleteTransactionConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    DeleteConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        deleteDialogTitle = stringResource(R.string.delete_dialog_transaction_title),
        deleteDialogDescription = stringResource(R.string.delete_dialog_transaction_description)
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
