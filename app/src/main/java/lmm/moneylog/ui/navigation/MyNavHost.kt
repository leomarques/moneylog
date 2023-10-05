package lmm.moneylog.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import lmm.moneylog.ui.convertTransactionTypeParam
import lmm.moneylog.ui.features.account.accountdetail.AccountDetailView
import lmm.moneylog.ui.features.account.getaccounts.GetAccountsView
import lmm.moneylog.ui.features.account.getaccounts.archive.GetArchivedAccountsView
import lmm.moneylog.ui.features.category.categorydetail.CategoryDetailView
import lmm.moneylog.ui.features.category.getccategories.GetCategoriesView
import lmm.moneylog.ui.features.home.HomeLayout
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsView
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailView

@Composable
fun MyNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    onHomeFabClick: () -> Unit,
    onBalanceCardClick: (String) -> Unit,
    onArrowBackClick: () -> Unit,
    onTransactionsFabClick: () -> Unit,
    onTransactionsItemClick: (Int) -> Unit,
    onAccountsFabClick: () -> Unit,
    onAccountsItemClick: (Int) -> Unit,
    onCategoriesFabClick: () -> Unit,
    onCategoriesItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit
) {
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(homeScreen) {
            HomeLayout(
                onFabClick = onHomeFabClick,
                onClick = onBalanceCardClick
            )
        }

        composableExt(
            route = "$getTransactionsScreen/{$paramTypeOfValue}",
            onArrowBackClick = onArrowBackClick
        ) { backStackEntry ->
            val param = backStackEntry?.arguments?.getString(paramTypeOfValue)
            val typeOfValue = convertTransactionTypeParam(param)

            GetTransactionsView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onTransactionsFabClick,
                typeOfValue = typeOfValue,
                onItemClick = onTransactionsItemClick
            )
        }

        composableExt(
            route = "$transactionDetailScreen?$paramId={$paramId}",
            onArrowBackClick = onArrowBackClick,
            arguments = listOf(
                navArgument(paramId) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )
        ) {
            TransactionDetailView(onArrowBackClick)
        }

        composableExt(
            route = getAccountsScreen,
            onArrowBackClick = onArrowBackClick
        ) {
            GetAccountsView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onAccountsFabClick,
                onItemClick = onAccountsItemClick,
                onArchivedIconClick = onArchivedIconClick
            )
        }

        composableExt(
            route = "$accountDetailScreen?$paramId={$paramId}",
            onArrowBackClick = onArrowBackClick,
            arguments = listOf(
                navArgument(paramId) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )
        ) {
            AccountDetailView(onArrowBackClick)
        }

        composableExt(
            route = getCategoriesScreen,
            onArrowBackClick = onArrowBackClick
        ) {
            GetCategoriesView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onCategoriesFabClick,
                onItemClick = onCategoriesItemClick
            )
        }

        composableExt(
            route = "$categoryDetailScreen?$paramId={$paramId}",
            onArrowBackClick = onArrowBackClick,
            arguments = listOf(
                navArgument(paramId) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )
        ) {
            CategoryDetailView(onArrowBackClick)
        }

        composableExt(
            route = getArchivedAccountsScreen,
            onArrowBackClick = onArrowBackClick
        ) {
            GetArchivedAccountsView(onArrowBackClick = onArrowBackClick)
        }
    }
}
