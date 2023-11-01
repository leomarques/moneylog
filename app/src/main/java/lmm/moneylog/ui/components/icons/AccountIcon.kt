package lmm.moneylog.ui.components.icons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun AccountIcon() {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.outline_account_balance_24),
        contentDescription = stringResource(R.string.account)
    )
}

@Preview
@Composable
fun AccountIconPreview() {
    AccountIcon()
}