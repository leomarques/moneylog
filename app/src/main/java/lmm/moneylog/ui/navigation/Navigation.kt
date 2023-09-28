package lmm.moneylog.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

const val homeScreen = "home"
const val transactionDetailScreen = "transaction_detail"
const val getTransactionsScreen = "get_transactions"
const val getAccountsScreen = "get_accounts"
const val getCategoriesScreen = "get_categories"
const val accountDetailScreen = "account_detail"
const val categoryDetailScreen = "category_detail"

const val paramTypeOfValue = "typeOfValue"
const val paramId = "id"

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    Scaffold(
        bottomBar = {
            MyNavigationBar(navController)
        }
    ) { paddingValues ->
        MyNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = startDestination
        )
    }
}
