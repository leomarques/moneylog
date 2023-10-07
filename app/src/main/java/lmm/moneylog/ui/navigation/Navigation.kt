package lmm.moneylog.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MyNavigationBar(
                selectedIndex = navBarSelectedIndex.intValue,
                onClick = { index ->
                    when (index) {
                        0 -> {
                            navController.navigatePopUpTo(
                                destination = homeScreen,
                                navBarSelectedIndex = navBarSelectedIndex
                            )
                        }

                        1 -> {
                            navController.navigatePopUpTo(
                                destination = "$getTransactionsScreen/$paramTypeAll",
                                navBarSelectedIndex = navBarSelectedIndex
                            )
                        }

                        2 -> {
                            navController.navigatePopUpTo(
                                destination = getAccountsScreen,
                                navBarSelectedIndex = navBarSelectedIndex
                            )
                        }

                        3 -> {
                            navController.navigatePopUpTo(
                                destination = getCategoriesScreen,
                                navBarSelectedIndex = navBarSelectedIndex
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
                navController.navigatePopUpTo(
                    destination = transactionDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onBalanceCardClick = { typeOfValue ->
                if (typeOfValue == paramTypeAll) {
                    navController.navigatePopUpTo(
                        destination = getAccountsScreen,
                        navBarSelectedIndex = navBarSelectedIndex
                    )
                } else {
                    navController.navigatePopUpTo(
                        destination = "$getTransactionsScreen/$typeOfValue",
                        navBarSelectedIndex = navBarSelectedIndex
                    )
                }
            },
            onArrowBackClick = {
                navController.popBackStack()
                navController.currentBackStackEntry?.destination?.route?.let {
                    navBarSelectedIndex.updateIndex(it)
                }
            },
            onTransactionsFabClick = {
                navController.navigatePopUpTo(
                    destination = transactionDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onTransactionsItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$transactionDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onAccountsFabClick = {
                navController.navigatePopUpTo(
                    destination = accountDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onAccountsItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$accountDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onCategoriesFabClick = {
                navController.navigatePopUpTo(
                    destination = categoryDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onCategoriesItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$categoryDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onArchivedIconClick = {
                navController.navigatePopUpTo(
                    destination = getArchivedAccountsScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            },
            onTransferIconClick = {
                navController.navigatePopUpTo(
                    destination = transferScreen,
                    navBarSelectedIndex = navBarSelectedIndex
                )
            }
        )
    }
}
