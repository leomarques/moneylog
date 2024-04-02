package lmm.moneylog.ui.features.account.transfer.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTransferTopBar(
    onArrowBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.topbar_transfer_add)) },
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.arrowback_desc)
                )
            }
        }
    )
}

@Preview
@Composable
fun AccountTransferTopBarPreview() {
    AccountTransferTopBar(onArrowBackClick = {})
}
