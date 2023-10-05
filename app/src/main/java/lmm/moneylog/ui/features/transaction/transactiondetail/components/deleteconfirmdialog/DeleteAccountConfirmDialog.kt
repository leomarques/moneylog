package lmm.moneylog.ui.features.transaction.transactiondetail.components.deleteconfirmdialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.features.transaction.transactiondetail.components.ConfirmDialog

@Composable
fun DeleteAccountConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.delete_dialog_account_title),
        description = stringResource(R.string.delete_dialog_account_description),
        icon = Icons.Default.Delete
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
