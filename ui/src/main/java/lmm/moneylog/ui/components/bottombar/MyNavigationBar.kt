package lmm.moneylog.ui.components.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lmm.moneylog.ui.components.bottombar.models.BottomBarItem

@Composable
fun MyNavigationBar(
    items: List<BottomBarItem>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    NavigationBar(modifier = modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = item.isSelected,
                onClick = {
                    onClick(index)
                }
            )
        }
    }
}
