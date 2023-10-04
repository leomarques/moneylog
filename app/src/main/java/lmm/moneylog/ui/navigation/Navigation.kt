package lmm.moneylog.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.R

const val homeScreen = "home"
const val transactionDetailScreen = "transaction_detail"
const val getTransactionsScreen = "get_transactions"
const val getAccountsScreen = "get_accounts"
const val getCategoriesScreen = "get_categories"
const val accountDetailScreen = "account_detail"
const val categoryDetailScreen = "category_detail"

const val paramTypeOfValue = "typeOfValue"
const val paramTypeAll = "all"
const val paramId = "id"

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    var navBarSelectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MyNavigationBar(
                items = listOf(
                    Pair(stringResource(R.string.navbar_home), Icons.Default.Home),
                    Pair(stringResource(R.string.navbar_transactions), Icons.Default.PlayArrow),
                    Pair(stringResource(R.string.navbar_accounts), Icons.Default.Person),
                    Pair(stringResource(R.string.navbar_categories), Icons.Default.Star)
                ),
                selectedIndex = navBarSelectedIndex,
                onClick = { index ->
                    when (index) {
                        0 -> {
                            navController.navigate(homeScreen)
                            navBarSelectedIndex = 0
                        }

                        1 -> {
                            navController.navigate("$getTransactionsScreen/$paramTypeAll")
                            navBarSelectedIndex = 1
                        }

                        2 -> {
                            navController.navigate(getAccountsScreen)
                            navBarSelectedIndex = 2
                        }

                        3 -> {
                            navController.navigate(getCategoriesScreen)
                            navBarSelectedIndex = 3
                        }

                        else -> {
                            navController.navigate(homeScreen)
                            navBarSelectedIndex = 0
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        MyNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
            onHomeFabClick = {
                navBarSelectedIndex = 1
                navController.navigate(transactionDetailScreen)
            },
            onBalanceCardClick = { typeOfValue ->
                navBarSelectedIndex = if (typeOfValue == paramTypeAll) {
                    navController.navigate(getAccountsScreen)
                    2
                } else {
                    navController.navigate("$getTransactionsScreen/$typeOfValue")
                    1
                }
            },
            onTransactionsFabClick = {
                navController.navigate(transactionDetailScreen)
                navBarSelectedIndex = 1
            },
            onTransactionsItemClick = { id ->
                navController.navigate("$transactionDetailScreen?$paramId=$id")
                navBarSelectedIndex = 1
            },
            onAccountsFabClick = {
                navController.navigate(accountDetailScreen)
                navBarSelectedIndex = 2
            },
            onAccountsItemClick = { id ->
                navController.navigate("$accountDetailScreen?$paramId=$id")
                navBarSelectedIndex = 2
            },
            onCategoriesFabClick = {
                navController.navigate(categoryDetailScreen)
                navBarSelectedIndex = 3
            },
            onCategoriesItemClick = { id ->
                navController.navigate("$categoryDetailScreen?$paramId=$id")
                navBarSelectedIndex = 3
            },
            onArrowBackClick = {
                navController.popBackStack()
            }
        )
    }
}
