package lmm.moneylog.ui.navigation.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag

@Composable
fun BaseNavigationBar(
    items: List<Pair<String, ImageVector>>,
    selectedIndex: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.testTag("BaseNavigationBarItem$index"),
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
