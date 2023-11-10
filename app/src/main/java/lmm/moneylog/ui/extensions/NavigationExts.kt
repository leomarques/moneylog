package lmm.moneylog.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import lmm.moneylog.ui.features.balancecard.view.layout.balanceCardIncome
import lmm.moneylog.ui.features.balancecard.view.layout.balanceCardOutcome
import lmm.moneylog.ui.features.transaction.list.viewmodel.getTransactionsAll
import lmm.moneylog.ui.features.transaction.list.viewmodel.getTransactionsIncome
import lmm.moneylog.ui.features.transaction.list.viewmodel.getTransactionsOutcome
import lmm.moneylog.ui.navigation.misc.BackPressHandler
import lmm.moneylog.ui.navigation.misc.accountDetailScreen
import lmm.moneylog.ui.navigation.misc.accountsListScreen
import lmm.moneylog.ui.navigation.misc.archivedAccountsListScreen
import lmm.moneylog.ui.navigation.misc.categoriesListScreen
import lmm.moneylog.ui.navigation.misc.categoryDetailScreen
import lmm.moneylog.ui.navigation.misc.homeScreen
import lmm.moneylog.ui.navigation.misc.transactionDetailScreen
import lmm.moneylog.ui.navigation.misc.transactionsListScreen

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
        homeScreen -> 0
        transactionsListScreen -> 1
        transactionDetailScreen -> 1
        accountsListScreen -> 2
        archivedAccountsListScreen -> 2
        accountDetailScreen -> 2
        categoriesListScreen -> 3
        categoryDetailScreen -> 3
        else -> 0
    }.also { intValue = it }
}

fun MutableState<Boolean>.updateShow(destination: String) {
    value = when (destination.split("/", "?")[0]) {
        homeScreen -> true
        transactionsListScreen -> true
        transactionDetailScreen -> false
        accountsListScreen -> true
        archivedAccountsListScreen -> true
        accountDetailScreen -> false
        categoriesListScreen -> true
        categoryDetailScreen -> false
        else -> true
    }
}

fun String?.toTransactionType() = when (this) {
    balanceCardIncome -> getTransactionsIncome
    balanceCardOutcome -> getTransactionsOutcome
    else -> getTransactionsAll
}
