package lmm.moneylog.ui.navigation.misc

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.ui.components.bottombar.BottomBar
import lmm.moneylog.ui.navigation.navhost.NavHostParams

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    pendingTransactionId: Long = -1L
) {
    val showNavigationBar = remember { mutableStateOf(true) }
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    // Listen to navigation changes and update state accordingly
    DisposableEffect(navController) {
        val listener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                destination.route?.let { route ->
                    navBarSelectedIndex.updateIndex(route)
                    showNavigationBar.updateShow(route)
                }
            }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    // Navigate to transaction detail if we have a pending transaction ID
    LaunchedEffect(pendingTransactionId) {
        if (pendingTransactionId > 0) {
            navController.navigate("$TRANSACTION_DETAIL_SCREEN?$PARAM_ID=$pendingTransactionId")
        }
    }

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
            }
        )
    }
}
