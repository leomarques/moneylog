package lmm.moneylog.ui.navigation.misc

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.ui.navigation.bottombar.BottomBar
import lmm.moneylog.ui.navigation.navhost.NavHostParams

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    val showNavigationBar = remember { mutableStateOf(true) }
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            if (showNavigationBar.value) {
                BottomBar(
                    navBarSelectedIndex = navBarSelectedIndex,
                    navController = navController,
                    showNavigationBar = showNavigationBar
                )
            }
        }
    ) { paddingValues ->
        NavHostParams(
            paddingValues = paddingValues,
            navController = navController,
            navBarSelectedIndex = navBarSelectedIndex,
            startDestination = startDestination,
            showNavigationBar = showNavigationBar
        )
    }
}
