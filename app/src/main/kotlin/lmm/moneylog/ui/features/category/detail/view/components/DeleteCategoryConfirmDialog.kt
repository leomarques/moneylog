package lmm.moneylog.ui.features.category.detail.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.ConfirmDialog

@Composable
fun DeleteCategoryConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        title = stringResource(R.string.dialog_title_category_delete),
        description = stringResource(R.string.dialog_desc_category_delete),
        icon = Icons.Default.Delete
    )
}

@Preview
@Composable
private fun DeleteCategoryConfirmDialogPreview() {
    DeleteCategoryConfirmDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
