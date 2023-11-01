package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailTopAppBar(
    stringId: Int,
    onArrowBackClick: () -> Unit,
    actions: @Composable (RowScope.() -> Unit)
) {
    TopAppBar(
        title = { Text(text = stringResource(stringId)) },
        navigationIcon = { ArrowBackIcon(onArrowBackClick) },
        actions = actions
    )
}

@Preview
@Composable
fun DetailTopAppBarPreview() {
    DetailTopAppBar(
        stringId = R.string.detail_topbar_account_add,
        onArrowBackClick = {},
        actions = {}
    )
}
