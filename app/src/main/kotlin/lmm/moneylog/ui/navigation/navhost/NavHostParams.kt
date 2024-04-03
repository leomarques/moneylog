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
import lmm.moneylog.ui.navigation.misc.ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.ACCOUNT_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.ARCHIVED_ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.PARAM_ID
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_ALL
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTION_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSFER_SCREEN

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
                destination = TRANSACTION_DETAIL_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onBalanceCardClick = { typeOfValue ->
            if (typeOfValue == PARAM_TYPE_ALL) {
                navController.navigatePopUpTo(
                    destination = ACCOUNTS_LIST_SCREEN,
                    navBarSelectedIndex = navBarSelectedIndex,
                    showNavigationBar = showNavigationBar
                )
            } else {
                navController.navigatePopUpTo(
                    destination = "$TRANSACTIONS_LIST_SCREEN/$typeOfValue",
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
                destination = TRANSACTION_DETAIL_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onTransactionsItemClick = { id ->
            navController.navigatePopUpTo(
                destination = "$TRANSACTION_DETAIL_SCREEN?$PARAM_ID=$id",
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onAccountsFabClick = {
            navController.navigatePopUpTo(
                destination = ACCOUNT_DETAIL_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onAccountsItemClick = { id ->
            navController.navigatePopUpTo(
                destination = "$ACCOUNT_DETAIL_SCREEN?$PARAM_ID=$id",
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onCategoriesFabClick = {
            navController.navigatePopUpTo(
                destination = CATEGORY_DETAIL_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onCategoriesItemClick = { id ->
            navController.navigatePopUpTo(
                destination = "$CATEGORY_DETAIL_SCREEN?$PARAM_ID=$id",
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onArchivedIconClick = {
            navController.navigatePopUpTo(
                destination = ARCHIVED_ACCOUNTS_LIST_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        },
        onTransferIconClick = {
            navController.navigatePopUpTo(
                destination = TRANSFER_SCREEN,
                navBarSelectedIndex = navBarSelectedIndex,
                showNavigationBar = showNavigationBar
            )
        }
    )
}
