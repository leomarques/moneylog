package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountsListTopBar(
    onArrowBackClick: () -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp),
        title = { Text(text = stringResource(id = R.string.common_accounts)) },
        navigationIcon = { ArrowBackIcon(onClick = onArrowBackClick) },
        actions = {
            IconButton(
                onClick = onArchivedIconClick,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_unarchive_24),
                        contentDescription = stringResource(R.string.accessibility_archive)
                    )
                }
            )
            IconButton(
                onClick = onTransferIconClick,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_currency_exchange_24),
                        contentDescription = stringResource(R.string.accessibility_transfer)
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun AccountsListTopBarPreview() {
    AccountsListTopBar(
        onArrowBackClick = {},
        onArchivedIconClick = {},
        onTransferIconClick = {}
    )
}
