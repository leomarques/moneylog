package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountsListTopBar(
    onArrowBackClick: () -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.accounts)) },
        navigationIcon = { ArrowBackIcon(onArrowBackClick) },
        actions = {
            IconButton(
                onClick = onArchivedIconClick,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_unarchive_24),
                        contentDescription = stringResource(R.string.archive_desc)
                    )
                }
            )
            IconButton(
                onClick = onTransferIconClick,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_currency_exchange_24),
                        contentDescription = stringResource(R.string.transfer_desc)
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun AccountsListTopBarPreview() {
    AccountsListTopBar(
        onArrowBackClick = {},
        onArchivedIconClick = {},
        onTransferIconClick = {}
    )
}
