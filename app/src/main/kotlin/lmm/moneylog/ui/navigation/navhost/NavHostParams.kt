package lmm.moneylog.ui.navigation.navhost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import lmm.moneylog.ui.extensions.navigatePopUpTo
import lmm.moneylog.ui.extensions.updateIndex
import lmm.moneylog.ui.extensions.updateShow
import lmm.moneylog.ui.navigation.misc.accountDetailScreen
import lmm.moneylog.ui.navigation.misc.accountsListScreen
import lmm.moneylog.ui.navigation.misc.archivedAccountsListScreen
import lmm.moneylog.ui.navigation.misc.categoryDetailScreen
import lmm.moneylog.ui.navigation.misc.paramId
import lmm.moneylog.ui.navigation.misc.paramTypeAll
import lmm.moneylog.ui.navigation.misc.transactionDetailScreen
import lmm.moneylog.ui.navigation.misc.transactionsListScreen
import lmm.moneylog.ui.navigation.misc.transferScreen

@Composable
fun NavHostParams(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    navBarSelectedIndex: MutableIntState,
    showNavigationBar: MutableState<Boolean>
) {
    MyNavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = startDestination,
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
                    destination = accountsListScreen,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            } else {
                navController.navigatePopUpTo(
                    destination = "$transactionsListScreen/$typeOfValue",
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            }
        },
        onArrowBackClick = {
            navController.popBackStack()
            navController.currentBackStackEntry?.destination?.route?.let {
                navBarSelectedIndex.updateIndex(it)
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
                destination = archivedAccountsListScreen,
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
