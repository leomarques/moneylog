package lmm.moneylog.ui.features.transaction.transactiondetail.components.deleteconfirmdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DeleteAccountConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    DeleteConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        deleteDialogTitle = stringResource(R.string.delete_dialog_account_title),
        deleteDialogDescription = stringResource(R.string.delete_dialog_account_description)
    )
}

@Preview
@Composable
fun DeleteAccountConfirmDialogPreview() {
    DeleteAccountConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
