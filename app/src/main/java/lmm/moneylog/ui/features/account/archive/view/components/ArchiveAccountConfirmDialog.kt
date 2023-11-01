package lmm.moneylog.ui.features.account.archive.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun ArchiveAccountConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_account_archive),
        description = stringResource(R.string.dialog_description_account_archive),
        icon = ImageVector.vectorResource(id = R.drawable.outline_archive_24)
    )
}

@Preview
@Composable
fun ArchiveAccountConfirmDialogPreview() {
    ArchiveAccountConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
