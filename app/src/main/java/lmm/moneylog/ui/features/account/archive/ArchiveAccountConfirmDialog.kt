package lmm.moneylog.ui.features.account.archive

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.ui.components.ConfirmDialog

@Composable
fun ArchiveAccountConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.archive_dialog_account_title),
        description = stringResource(R.string.archive_dialog_account_description),
        icon = Icons.Default.Build
    )
}
