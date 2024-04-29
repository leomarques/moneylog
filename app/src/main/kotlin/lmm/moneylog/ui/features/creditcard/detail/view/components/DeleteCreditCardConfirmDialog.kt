package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun DeleteCreditCardConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_creditcard_delete),
        description = stringResource(R.string.dialog_description_creditcard_delete),
        icon = Icons.Default.Delete
    )
}

@Preview
@Composable
private fun DeleteCreditCardConfirmDialogPreview() {
    DeleteCreditCardConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
