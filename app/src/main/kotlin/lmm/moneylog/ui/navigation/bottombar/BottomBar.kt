package lmm.moneylog.ui.navigation.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import lmm.moneylog.ui.navigation.misc.ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORIES_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CREDITCARD_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.HOME_SCREEN
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_ALL
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.navigatePopUpTo

@Composable
fun BottomBar(
    navBarSelectedIndex: MutableIntState,
    navController: NavHostController,
    showNavigationBar: MutableState<Boolean>
) {
    MyNavigationBar(
        selectedIndex = navBarSelectedIndex.intValue,
        onClick = { index ->
            when (index) {
                0 -> {
                    navController.navigatePopUpTo(
                        destination = HOME_SCREEN,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                1 -> {
                    navController.navigatePopUpTo(
                        destination = "$TRANSACTIONS_LIST_SCREEN/$PARAM_TYPE_ALL",
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                2 -> {
                    navController.navigatePopUpTo(
                        destination = ACCOUNTS_LIST_SCREEN,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                3 -> {
                    navController.navigatePopUpTo(
                        destination = CREDITCARD_LIST_SCREEN,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                4 -> {
                    navController.navigatePopUpTo(
                        destination = CATEGORIES_LIST_SCREEN,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(
        navBarSelectedIndex = remember { mutableIntStateOf(0) },
        navController = NavHostController(LocalContext.current),
        showNavigationBar = remember { mutableStateOf(true) }
    )
}
