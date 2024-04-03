package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailTopAppBar(
    isEdit: Boolean,
    onArrowBackClick: () -> Unit,
    onArchiveIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.detail_topbar_account_edit
                        } else {
                            R.string.detail_topbar_account_add
                        }
                    )
            )
        },
        navigationIcon = { ArrowBackIcon(onArrowBackClick) },
        actions = {
            if (isEdit) {
                ArchiveActionButton(onArchiveIconClick)
            }
        }
    )
}

@Preview
@Composable
fun AccountDetailTopAppBarPreview() {
    AccountDetailTopAppBar(
        isEdit = true,
        onArrowBackClick = {},
        onArchiveIconClick = {}
    )
}
