package lmm.moneylog.ui.features.invoice.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun AdjustInvoiceConfirmDialog(
    adjustmentValue: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_invoice_adjust_value),
        description = stringResource(R.string.dialog_desc_invoice_adjust_value, adjustmentValue),
        icon = ImageVector.vectorResource(id = R.drawable.outline_attach_money_24)
    )
}

@Preview
@Composable
private fun AdjustInvoiceConfirmDialogPreview() {
    AdjustInvoiceConfirmDialog(
        adjustmentValue = "R$ 150,00",
        onConfirm = {},
        onDismiss = {}
    )
}
