package lmm.moneylog.ui.navigation.bottombar

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import lmm.moneylog.ui.extensions.navigatePopUpTo
import lmm.moneylog.ui.navigation.misc.accountsListScreen
import lmm.moneylog.ui.navigation.misc.categoriesListScreen
import lmm.moneylog.ui.navigation.misc.homeScreen
import lmm.moneylog.ui.navigation.misc.paramTypeAll
import lmm.moneylog.ui.navigation.misc.transactionsListScreen

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
                        destination = homeScreen,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                1 -> {
                    navController.navigatePopUpTo(
                        destination = "$transactionsListScreen/$paramTypeAll",
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                2 -> {
                    navController.navigatePopUpTo(
                        destination = accountsListScreen,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }

                3 -> {
                    navController.navigatePopUpTo(
                        destination = categoriesListScreen,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(
        navBarSelectedIndex = mutableIntStateOf(0),
        navController = NavHostController(LocalContext.current),
        showNavigationBar = mutableStateOf(true)
    )
}
