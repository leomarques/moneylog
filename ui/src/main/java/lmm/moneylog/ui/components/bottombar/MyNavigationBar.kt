package lmm.moneylog.ui.components.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

@Composable
fun MyNavigationBar(
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    BaseNavigationBar(
        items =
            listOf(
                Pair(
                    stringResource(R.string.ui_nav_bottom_home),
                    Icons.Default.Home
                ),
                Pair(
                    stringResource(R.string.ui_nav_bottom_transactions),
                    ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24)
                ),
                Pair(
                    stringResource(R.string.ui_nav_bottom_settings),
                    ImageVector.vectorResource(id = R.drawable.outline_settings_24)
                )
            ),
        selectedIndex = selectedIndex,
        onClick = onClick
    )
}

@Preview
@Composable
private fun MyNavigationBarPreview() {
    MyNavigationBar(selectedIndex = 0) {}
}
