package lmm.moneylog.ui.features.account.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.DetailTopAppBar

@Composable
fun AccountDetailTopAppBar(
    isEdit: Boolean,
    onArrowBackClick: () -> Unit,
    onArchiveIconClick: () -> Unit
) {
    DetailTopAppBar(
        stringId = if (isEdit) {
            R.string.detail_topbar_account_edit
        } else {
            R.string.detail_topbar_account_add
        },
        onArrowBackClick = onArrowBackClick,
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
        isEdit = false,
        onArrowBackClick = {},
        onArchiveIconClick = {}
    )
}
