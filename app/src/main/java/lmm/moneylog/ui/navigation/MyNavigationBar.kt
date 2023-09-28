package lmm.moneylog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import lmm.moneylog.R

@Composable
fun MyNavigationBar(navHostController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        Pair(stringResource(R.string.navbar_home), Icons.Default.Home),
        Pair(stringResource(R.string.navbar_transactions), Icons.Default.PlayArrow),
        Pair(stringResource(R.string.navbar_accounts), Icons.Default.Person),
        Pair(stringResource(R.string.navbar_categories), Icons.Default.Star)
    )

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
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index

                    navHostController.navigate(
                        when (index) {
                            0 -> homeScreen
                            1 -> "$getTransactionsScreen/all"
                            2 -> getAccountsScreen
                            3 -> getCategoriesScreen
                            else -> homeScreen
                        }
                    )
                }
            )
        }
    }
}
