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
        HOME_SCREEN -> HOME_INDEX
        TRANSACTIONS_LIST_SCREEN -> TRANSACTIONS_INDEX
        TRANSACTION_DETAIL_SCREEN -> TRANSACTIONS_INDEX
        ACCOUNTS_LIST_SCREEN -> ACCOUNTS_INDEX
        ACCOUNT_DETAIL_SCREEN -> ACCOUNTS_INDEX
        ARCHIVED_ACCOUNTS_LIST_SCREEN -> ACCOUNTS_INDEX
        CREDITCARD_LIST_SCREEN -> CREDITCARD_INDEX
        CREDITCARD_DETAIL_SCREEN -> CREDITCARD_INDEX
        CATEGORIES_LIST_SCREEN -> CATEGORIES_INDEX
        CATEGORY_DETAIL_SCREEN -> CATEGORIES_INDEX
        INVOICE_LIST_SCREEN -> TRANSACTIONS_INDEX
        else -> HOME_INDEX
    }.also { intValue = it }
}

fun MutableState<Boolean>.updateShow(destination: String) {
    value =
        when (destination.split("/", "?")[0]) {
            HOME_SCREEN -> true
            TRANSACTIONS_LIST_SCREEN -> true
            TRANSACTION_DETAIL_SCREEN -> false
            ACCOUNTS_LIST_SCREEN -> true
            ACCOUNT_DETAIL_SCREEN -> false
            ARCHIVED_ACCOUNTS_LIST_SCREEN -> true
            CATEGORIES_LIST_SCREEN -> true
            CATEGORY_DETAIL_SCREEN -> false
            CREDITCARD_LIST_SCREEN -> true
            CREDITCARD_DETAIL_SCREEN -> false
            INVOICE_LIST_SCREEN -> true
            else -> true
        }
}

fun String?.toTransactionType() =
    when (this) {
        BALANCE_CARD_INCOME -> GET_TRANSACTIONS_INCOME
        BALANCE_CARD_OUTCOME -> GET_TRANSACTIONS_OUTCOME
        else -> GET_TRANSACTIONS_ALL
    }
