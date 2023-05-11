package lmm.moneylog.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.ui.features.addtransaction.AddTransactionView
import lmm.moneylog.ui.features.gettransactions.GetTransactionsView
import lmm.moneylog.ui.features.home.HomeScreen

const val homeScreen = "home"
const val addTransactionScreen = "add_transaction"
const val getTransactionsScreen = "get_transactions"

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(homeScreen) {
            HomeScreen(
                onFabClick = {
                    navController.navigate(addTransactionScreen)
                },
                onAmountClick = {
                    navController.navigate(getTransactionsScreen)
                }
            )
        }

        composable(addTransactionScreen) {
            AddTransactionView(
                onArrowBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(getTransactionsScreen) {
            GetTransactionsView(
                onArrowBackClick = {
                    navController.popBackStack()
                },
                onFabClick = {
                    navController.navigate(addTransactionScreen)
                }
            )
        }
    }
}
