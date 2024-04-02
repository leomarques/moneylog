package lmm.moneylog.ui.features.account.archive.view.components

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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArchivedAccountsTopBar(onArrowBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.topbar_archived_accounts))
        },
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
fun ArchivedAccountsTopBarPreview() {
    ArchivedAccountsTopBar(onArrowBackClick = {})
}
