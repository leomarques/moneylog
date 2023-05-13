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
import lmm.moneylog.ui.features.home.HomeLayout

const val homeScreen = "home"
const val addTransactionScreen = "add_transaction"
const val paramTypeOfValue = "typeOfValue"
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
            HomeLayout(
                onFabClick = {
                    navController.navigate(addTransactionScreen)
                },
                onClick = { typeOfValue ->
                    navController.navigate("$getTransactionsScreen/$typeOfValue")
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

        composable("$getTransactionsScreen/{$paramTypeOfValue}") { backStackEntry ->
            val param = backStackEntry.arguments?.getString(paramTypeOfValue)
            val typeOfValue = convertTransactionTypeParam(param)

            GetTransactionsView(
                onArrowBackClick = {
                    navController.popBackStack()
                },
                onFabClick = {
                    navController.navigate(addTransactionScreen)
                },
                typeOfValue = typeOfValue
            )
        }
    }
}
