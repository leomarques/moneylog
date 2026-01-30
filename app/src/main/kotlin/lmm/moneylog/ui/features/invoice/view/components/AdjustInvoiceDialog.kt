package lmm.moneylog.ui.features.invoice.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.dialogs.AdjustValueDialog
import lmm.moneylog.ui.features.category.list.model.CategoryModel

@Composable
fun AdjustInvoiceDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int?) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<CategoryModel> = emptyList()
) {
    AdjustValueDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        title = stringResource(R.string.invoice_adjust_value_title),
        description = stringResource(R.string.invoice_adjust_value_description),
        inputLabel = stringResource(R.string.invoice_adjust_value_new),
        categories = categories,
        showCategorySelector = false,
        modifier = modifier
    )
}

@Preview
@Composable
private fun AdjustInvoiceDialogPreview() {
    AdjustInvoiceDialog(
        onDismiss = {},
        onConfirm = { _, _ -> }
    )
}
