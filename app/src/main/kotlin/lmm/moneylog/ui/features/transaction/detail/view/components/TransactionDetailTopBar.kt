package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon
import lmm.moneylog.ui.components.icons.DeleteIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransactionDetailTopBar(
    isEdit: Boolean,
    onDeleteClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.detail_topbar_transaction_edit
                        } else {
                            R.string.detail_topbar_transaction_add
                        }
                    )
            )
        },
        navigationIcon = { ArrowBackIcon(onClick = onArrowBackClick) },
        actions = {
            if (isEdit) DeleteIcon(onDeleteClick = onDeleteClick)
        }
    )
}

@Preview
@Composable
private fun TransactionDetailTopBarPreview() {
    TransactionDetailTopBar(
        isEdit = true,
        onDeleteClick = {},
        onArrowBackClick = {}
    )
}
