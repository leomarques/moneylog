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
import lmm.moneylog.ui.features.account.getaccounts.GetAccountsView
import lmm.moneylog.ui.features.category.getccategories.GetCategoriesView
import lmm.moneylog.ui.features.home.HomeLayout
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsView
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailView

const val homeScreen = "home"
const val transactionDetailScreen = "transaction_detail"
const val getTransactionsScreen = "get_transactions"
const val getAccountsScreen = "get_accounts"
const val getCategoriesScreen = "get_categories"

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
                    if (typeOfValue == "all") {
                        navController.navigate(getAccountsScreen)
                    } else {
                        navController.navigate("$getTransactionsScreen/$typeOfValue")
                    }
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

        composable(getAccountsScreen) {
            GetAccountsView(
                onArrowBackClick = {
                    navController.popBackStack()
                },
                onFabClick = {
//                    navController.navigate(transactionDetailScreen)
                },
                onItemClick = { id ->
//                    navController.navigate("$transactionDetailScreen?$paramId=$id")
                }
            )
        }

        composable(getCategoriesScreen) {
            GetCategoriesView(
                onArrowBackClick = {
                    navController.popBackStack()
                },
                onFabClick = {
//                    navController.navigate(transactionDetailScreen)
                },
                onItemClick = { id ->
//                    navController.navigate("$transactionDetailScreen?$paramId=$id")
                }
            )
        }
    }
}
