package lmm.moneylog.ui.extensions

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
import lmm.moneylog.ui.navigation.misc.ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.ACCOUNT_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.ARCHIVED_ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.BackPressHandler
import lmm.moneylog.ui.navigation.misc.CATEGORIES_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.HOME_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTION_DETAIL_SCREEN

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
        BackPressHandler(onBackPressed = onArrowBackClick)
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
    when (destination.split("/", "?")[0]) {
        HOME_SCREEN -> 0
        TRANSACTIONS_LIST_SCREEN -> 1
        TRANSACTION_DETAIL_SCREEN -> 1
        ACCOUNTS_LIST_SCREEN -> 2
        ARCHIVED_ACCOUNTS_LIST_SCREEN -> 2
        ACCOUNT_DETAIL_SCREEN -> 2
        CATEGORIES_LIST_SCREEN -> 3
        CATEGORY_DETAIL_SCREEN -> 3
        else -> 0
    }.also { intValue = it }
}

fun MutableState<Boolean>.updateShow(destination: String) {
    value =
        when (destination.split("/", "?")[0]) {
            HOME_SCREEN -> true
            TRANSACTIONS_LIST_SCREEN -> true
            TRANSACTION_DETAIL_SCREEN -> false
            ACCOUNTS_LIST_SCREEN -> true
            ARCHIVED_ACCOUNTS_LIST_SCREEN -> true
            ACCOUNT_DETAIL_SCREEN -> false
            CATEGORIES_LIST_SCREEN -> true
            CATEGORY_DETAIL_SCREEN -> false
            else -> true
        }
}

fun String?.toTransactionType() =
    when (this) {
        BALANCE_CARD_INCOME -> GET_TRANSACTIONS_INCOME
        BALANCE_CARD_OUTCOME -> GET_TRANSACTIONS_OUTCOME
        else -> GET_TRANSACTIONS_ALL
    }