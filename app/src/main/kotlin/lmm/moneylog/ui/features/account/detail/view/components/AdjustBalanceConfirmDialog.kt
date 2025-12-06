package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun AdjustBalanceConfirmDialog(
    adjustmentValue: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_account_adjust_balance),
        description = stringResource(R.string.dialog_desc_account_adjust_balance, adjustmentValue),
        icon = ImageVector.vectorResource(id = R.drawable.outline_attach_money_24)
    )
}

@Preview
@Composable
private fun AdjustBalanceConfirmDialogPreview() {
    AdjustBalanceConfirmDialog(
        adjustmentValue = "R$ 150,00",
        onConfirm = {},
        onDismiss = {}
    )
}
