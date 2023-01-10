package lmm.moneylog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.features.addtransaction.AddTransactionScreen
import lmm.moneylog.features.home.HomeScreen

const val homeScreen = "home"
const val addTransactionScreen = "add_transaction"

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(homeScreen) {
            HomeScreen({
                navController.navigate(addTransactionScreen)
            }
            )
        }

        composable(addTransactionScreen) {
            AddTransactionScreen(
                onArrowBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
