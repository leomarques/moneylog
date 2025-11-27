package lmm.moneylog.ui.components.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

@Composable
fun AppNavigationBar(
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    BaseNavigationBar(
        items =
            listOf(
                Pair(
                    stringResource(R.string.navbar_home),
                    Icons.Default.Home
                ),
                Pair(
                    stringResource(R.string.transactions),
                    ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24)
                ),
                Pair(
                    stringResource(R.string.options),
                    Icons.Default.Settings
                ),
            ),
        selectedIndex = selectedIndex,
        onClick = onClick
    )
}

@Preview
@Composable
private fun AppNavigationBarPreview() {
    AppNavigationBar(selectedIndex = 0) {}
}
