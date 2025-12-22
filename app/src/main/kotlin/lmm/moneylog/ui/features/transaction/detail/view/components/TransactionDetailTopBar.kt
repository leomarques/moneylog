package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon
import lmm.moneylog.ui.components.icons.DeleteIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransactionDetailTopBar(
    isEdit: Boolean,
    onDeleteClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp),
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.transaction_topbar_edit
                        } else {
                            R.string.transaction_topbar_add
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TransactionDetailTopBarPreview() {
    TransactionDetailTopBar(
        isEdit = true,
        onDeleteClick = {},
        onArrowBackClick = {}
    )
}
