package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.dialogs.AdjustValueDialog
import lmm.moneylog.ui.features.category.list.model.CategoryModel

@Composable
fun AdjustBalanceDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int?) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<CategoryModel> = emptyList()
) {
    AdjustValueDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        title = stringResource(R.string.account_adjust_balance_title),
        description = stringResource(R.string.account_adjust_balance_description),
        inputLabel = stringResource(R.string.account_adjust_balance_new),
        categories = categories,
        showCategorySelector = false,
        modifier = modifier
    )
}

@Preview
@Composable
private fun AdjustBalanceDialogPreview() {
    AdjustBalanceDialog(
        onDismiss = {},
        onConfirm = { _, _ -> }
    )
}
