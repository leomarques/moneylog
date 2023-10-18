package lmm.moneylog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun MyNavigationBar(
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    BaseNavigationBar(
        items = listOf(
            Pair(
                stringResource(R.string.navbar_home),
                Icons.Default.Home
            ),
            Pair(
                stringResource(R.string.navbar_transactions),
                ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24)
            ),
            Pair(
                stringResource(R.string.navbar_accounts),
                ImageVector.vectorResource(id = R.drawable.outline_account_balance_24)
            ),
            Pair(
                stringResource(R.string.navbar_categories),
                ImageVector.vectorResource(id = R.drawable.outline_category_24)
            )
        ),
        selectedIndex = selectedIndex,
        onClick = onClick
    )
}

@Composable
fun BaseNavigationBar(
    items: List<Pair<String, ImageVector>>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first
                    )
                },
                label = { Text(item.first) },
                selected = selectedIndex == index,
                onClick = {
                    onClick(index)
                }
            )
        }
    }
}

@Preview
@Composable
fun MyNavigationBarPreview() {
    MyNavigationBar(selectedIndex = 0) {}
}
