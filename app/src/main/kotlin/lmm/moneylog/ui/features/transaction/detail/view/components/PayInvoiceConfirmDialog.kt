package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun PayInvoiceConfirmDialog(
    value: String,
    account: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.invoice_pay_dialog_title),
        description = stringResource(R.string.invoice_pay_dialog_description, value, account),
        icon = Icons.Default.Done
    )
}

@Preview
@Composable
private fun PayInvoiceConfirmDialogPreview() {
    PayInvoiceConfirmDialog(
        onConfirm = {},
        onDismiss = {},
        value = "R$100,00",
        account = "Nubank"
    )
}
