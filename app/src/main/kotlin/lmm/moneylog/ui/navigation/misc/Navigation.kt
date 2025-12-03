package lmm.moneylog.ui.navigation.misc

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.ui.components.bottombar.BottomBar
import lmm.moneylog.ui.navigation.navhost.NavHostParams

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val showNavigationBar = remember { mutableStateOf(true) }
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showNavigationBar.value) {
                BottomBar(
                    selectedIndex = navBarSelectedIndex.intValue,
                    onNavigate = { destination ->
                        navController.navigatePopUpTo(
                            destination = destination,
                            navBarSelectedIndex = navBarSelectedIndex,
                            showNavigationBar = showNavigationBar
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHostParams(
            paddingValues = paddingValues,
            navController = navController,
            startDestination = HOME_SCREEN,
            onNavigate = { destination ->
                navController.navigatePopUpTo(
                    destination = destination,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onBackNavigation = {
                navController.popBackStack()
                navController.currentBackStackEntry?.destination?.route?.let { route ->
                    navBarSelectedIndex.updateIndex(route)
                    showNavigationBar.updateShow(route)
                }
            }
        )
    }
}
