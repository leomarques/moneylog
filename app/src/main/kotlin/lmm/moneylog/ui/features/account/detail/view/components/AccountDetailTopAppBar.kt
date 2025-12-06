package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailTopAppBar(
    isEdit: Boolean,
    onArrowBackClick: () -> Unit,
    onArchiveIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp),
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.account_topbar_edit
                        } else {
                            R.string.account_topbar_add
                        }
                    )
            )
        },
        navigationIcon = {
            ArrowBackIcon(onClick = onArrowBackClick)
        },
        actions = {
            if (isEdit) {
                ArchiveActionButton(onClick = onArchiveIconClick)
            }
        }
    )
}

@Preview
@Composable
private fun AccountDetailTopAppBarPreview() {
    AccountDetailTopAppBar(
        isEdit = true,
        onArrowBackClick = {},
        onArchiveIconClick = {}
    )
}
