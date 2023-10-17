package lmm.moneylog.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreen
) {
    val showNavigationBar = remember { mutableStateOf(true) }
    val navBarSelectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            if (showNavigationBar.value) {
                MyNavigationBar(
                    selectedIndex = navBarSelectedIndex.intValue,
                    onClick = { index ->
                        when (index) {
                            0 -> {
                                navController.navigatePopUpTo(
                                    destination = homeScreen,
                                    navBarSelectedIndex = navBarSelectedIndex,
                                    showNavigationBar = showNavigationBar
                                )
                            }

                            1 -> {
                                navController.navigatePopUpTo(
                                    destination = "$getTransactionsScreen/$paramTypeAll",
                                    navBarSelectedIndex = navBarSelectedIndex,
                                    showNavigationBar = showNavigationBar
                                )
                            }

                            2 -> {
                                navController.navigatePopUpTo(
                                    destination = getAccountsScreen,
                                    navBarSelectedIndex = navBarSelectedIndex,
                                    showNavigationBar = showNavigationBar
                                )
                            }

                            3 -> {
                                navController.navigatePopUpTo(
                                    destination = getCategoriesScreen,
                                    navBarSelectedIndex = navBarSelectedIndex,
                                    showNavigationBar = showNavigationBar
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        MyNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
            onHomeFabClick = {
                navController.navigatePopUpTo(
                    destination = transactionDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onBalanceCardClick = { typeOfValue ->
                if (typeOfValue == paramTypeAll) {
                    navController.navigatePopUpTo(
                        destination = getAccountsScreen,
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                } else {
                    navController.navigatePopUpTo(
                        destination = "$getTransactionsScreen/$typeOfValue",
                        navBarSelectedIndex = navBarSelectedIndex,
                        showNavigationBar = showNavigationBar
                    )
                }
            },
            onArrowBackClick = {
                navController.popBackStack()
                navController.currentBackStackEntry?.destination?.route?.let {
                    navBarSelectedIndex.updateShow(it)
                    showNavigationBar.updateShow(it)
                }
            },
            onTransactionsFabClick = {
                navController.navigatePopUpTo(
                    destination = transactionDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onTransactionsItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$transactionDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onAccountsFabClick = {
                navController.navigatePopUpTo(
                    destination = accountDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onAccountsItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$accountDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onCategoriesFabClick = {
                navController.navigatePopUpTo(
                    destination = categoryDetailScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onCategoriesItemClick = { id ->
                navController.navigatePopUpTo(
                    destination = "$categoryDetailScreen?$paramId=$id",
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onArchivedIconClick = {
                navController.navigatePopUpTo(
                    destination = getArchivedAccountsScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            },
            onTransferIconClick = {
                navController.navigatePopUpTo(
                    destination = transferScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            }
        )
    }
}
