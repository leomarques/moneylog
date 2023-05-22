package lmm.moneylog.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import lmm.moneylog.ui.features.gettransactions.GetTransactionsView
import lmm.moneylog.ui.features.home.HomeLayout
import lmm.moneylog.ui.features.transactiondetail.TransactionDetailView

const val homeScreen = "home"
const val transactionDetailScreen = "transaction_detail"
const val getTransactionsScreen = "get_transactions"

const val paramTypeOfValue = "typeOfValue"
const val paramId = "id"

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
                    navController.navigate(transactionDetailScreen)
                },
                onClick = { typeOfValue ->
                    navController.navigate("$getTransactionsScreen/$typeOfValue")
                }
            )
        }

        composable(
            route = "$transactionDetailScreen?$paramId={$paramId}",
            arguments = listOf(
                navArgument(paramId) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )
        ) {
            TransactionDetailView(
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
                    navController.navigate(transactionDetailScreen)
                },
                typeOfValue = typeOfValue,
                onItemClick = { id ->
                    navController.navigate("$transactionDetailScreen?$paramId=$id")
                }
            )
        }
    }
}
