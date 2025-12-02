package lmm.moneylog.ui.navigation.misc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import lmm.moneylog.ui.features.balancecard.view.layout.BALANCE_CARD_INCOME
import lmm.moneylog.ui.features.balancecard.view.layout.BALANCE_CARD_OUTCOME
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_ALL
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_INCOME
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_OUTCOME

fun NavGraphBuilder.composableExt(
    route: String,
    arguments: List<NamedNavArgument>? = null,
    onArrowBackClick: () -> Unit,
    content: @Composable (NavBackStackEntry?) -> Unit
) {
    composable(
        route = route,
        arguments = arguments ?: emptyList()
    ) {
        BackPressHandler(onBackPress = onArrowBackClick)
        content(it)
    }
}

fun NavHostController.navigatePopUpTo(
    destination: String,
    navBarSelectedIndex: MutableIntState,
    showNavigationBar: MutableState<Boolean>
) {
    navigate(destination) {
        launchSingleTop = true
        popUpTo(destination) {
            inclusive = true
        }
    }

    navBarSelectedIndex.updateIndex(destination)
    showNavigationBar.updateShow(destination)
}

fun MutableIntState.updateIndex(destination: String) {
    val route = destination.split("/", "?")[0]
    intValue =
        when (route) {
            HOME_SCREEN -> HOME_INDEX
            in transactionScreens() -> TRANSACTIONS_INDEX
            in settingsScreens() -> SETTINGS_INDEX
            else -> HOME_INDEX
        }
}

private fun transactionScreens() =
    setOf(
        TRANSACTIONS_LIST_SCREEN,
        TRANSACTION_DETAIL_SCREEN,
        INVOICE_LIST_SCREEN
    )

private fun settingsScreens() =
    setOf(
        SETTINGS_SCREEN,
        ACCOUNTS_LIST_SCREEN,
        ACCOUNT_DETAIL_SCREEN,
        ARCHIVED_ACCOUNTS_LIST_SCREEN,
        CREDITCARD_LIST_SCREEN,
        CREDITCARD_DETAIL_SCREEN,
        CATEGORIES_LIST_SCREEN,
        CATEGORY_DETAIL_SCREEN,
        NOTIFICATION_SETTINGS_SCREEN,
        CATEGORY_KEYWORDS_SCREEN,
        GRAPHS_SCREEN,
        TRANSFER_SCREEN
    )

fun MutableState<Boolean>.updateShow(destination: String) {
    val route = destination.split("/", "?")[0]
    value =
        when {
            route in screensWithBottomNav() -> true
            else -> false
        }
}

private fun screensWithBottomNav() =
    setOf(
        HOME_SCREEN,
        TRANSACTIONS_LIST_SCREEN,
        SETTINGS_SCREEN
    )

fun String?.toTransactionType() =
    when (this) {
        BALANCE_CARD_INCOME -> GET_TRANSACTIONS_INCOME
        BALANCE_CARD_OUTCOME -> GET_TRANSACTIONS_OUTCOME
        else -> GET_TRANSACTIONS_ALL
    }
