package lmm.moneylog.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MyNavigationBar(
                items = listOf(
                    Pair(stringResource(R.string.navbar_home), Icons.Default.Home),
                    Pair(stringResource(R.string.navbar_transactions), Icons.Default.PlayArrow),
                    Pair(stringResource(R.string.navbar_accounts), Icons.Default.Person),
                    Pair(stringResource(R.string.navbar_categories), Icons.Default.Star)
                ),
                selectedIndex = navBarSelectedIndex.intValue,
                onClick = { index ->
                    when (index) {
                        0 -> {
                            navigate(
                                navController = navController,
                                navBarSelectedIndex = navBarSelectedIndex,
                                destination = homeScreen
                            )
                        }

                        1 -> {
                            navigate(
                                navController = navController,
                                navBarSelectedIndex = navBarSelectedIndex,
                                destination = "$getTransactionsScreen/$paramTypeAll"
                            )
                        }

                        2 -> {
                            navigate(
                                navController = navController,
                                navBarSelectedIndex = navBarSelectedIndex,
                                destination = getAccountsScreen
                            )
                        }

                        3 -> {
                            navigate(
                                navController = navController,
                                navBarSelectedIndex = navBarSelectedIndex,
                                destination = getCategoriesScreen
                            )
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
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = transactionDetailScreen
                )
            },
            onBalanceCardClick = { typeOfValue ->
                if (typeOfValue == paramTypeAll) {
                    navigate(
                        navController = navController,
                        navBarSelectedIndex = navBarSelectedIndex,
                        destination = getAccountsScreen
                    )
                } else {
                    navigate(
                        navController = navController,
                        navBarSelectedIndex = navBarSelectedIndex,
                        destination = "$getTransactionsScreen/$typeOfValue"
                    )
                }
            },
            onTransactionsFabClick = {
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = transactionDetailScreen
                )
            },
            onTransactionsItemClick = { id ->
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = "$transactionDetailScreen?$paramId=$id"
                )
            },
            onAccountsFabClick = {
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = accountDetailScreen
                )
            },
            onAccountsItemClick = { id ->
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = "$accountDetailScreen?$paramId=$id"
                )
            },
            onCategoriesFabClick = {
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = categoryDetailScreen
                )
            },
            onCategoriesItemClick = { id ->
                navigate(
                    navController = navController,
                    navBarSelectedIndex = navBarSelectedIndex,
                    destination = "$categoryDetailScreen?$paramId=$id"
                )
            },
            onArrowBackClick = {
                navController.popBackStack()
                navController.currentBackStackEntry?.destination?.route?.let {
                    updateIndex(
                        it,
                        navBarSelectedIndex
                    )
                }
            }
        )
    }
}

private fun navigate(
    navController: NavHostController,
    navBarSelectedIndex: MutableIntState,
    destination: String
) {
    navController.navigate(destination) {
        launchSingleTop = true
        popUpTo(destination) {
            inclusive = true
        }
    }

    updateIndex(
        destination = destination,
        navBarSelectedIndex = navBarSelectedIndex
    )
}

private fun updateIndex(
    destination: String,
    navBarSelectedIndex: MutableIntState
) {
    when (destination.split("/", "?")[0]) {
        homeScreen -> 0
        getTransactionsScreen -> 1
        transactionDetailScreen -> 1
        getAccountsScreen -> 2
        accountDetailScreen -> 2
        getCategoriesScreen -> 3
        categoryDetailScreen -> 3
        else -> 0
    }.also { navBarSelectedIndex.intValue = it }
}
