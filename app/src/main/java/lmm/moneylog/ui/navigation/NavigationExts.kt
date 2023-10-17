package lmm.moneylog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

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
        getTransactionsScreen -> 1
        transactionDetailScreen -> 1
        getAccountsScreen -> 2
        getArchivedAccountsScreen -> 2
        accountDetailScreen -> 2
        getCategoriesScreen -> 3
        categoryDetailScreen -> 3
        else -> 0
    }.also { intValue = it }
}

fun MutableState<Boolean>.updateShow(destination: String) {
    value = when (destination.split("/", "?")[0]) {
        homeScreen -> true
        getTransactionsScreen -> true
        transactionDetailScreen -> false
        getAccountsScreen -> true
        getArchivedAccountsScreen -> true
        accountDetailScreen -> false
        getCategoriesScreen -> true
        categoryDetailScreen -> false
        else -> true
    }
}
