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
import lmm.moneylog.ui.features.account.archive.view.layout.ArchivedAccountsListView
import lmm.moneylog.ui.features.account.detail.view.layout.AccountDetailView
import lmm.moneylog.ui.features.account.list.view.layout.AccountsListView
import lmm.moneylog.ui.features.account.transfer.view.layout.AccountTransferView
import lmm.moneylog.ui.features.category.detail.view.layout.CategoryDetailView
import lmm.moneylog.ui.features.category.list.view.layouts.CategoriesListView
import lmm.moneylog.ui.features.home.view.HomeView
import lmm.moneylog.ui.features.transaction.detail.view.layout.TransactionDetailView
import lmm.moneylog.ui.features.transaction.list.view.layout.GetTransactionsView

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
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit
) {
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(homeScreen) {
            HomeView(
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
            AccountsListView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onAccountsFabClick,
                onItemClick = onAccountsItemClick,
                onArchivedIconClick = onArchivedIconClick,
                onTransferIconClick = onTransferIconClick
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
            CategoriesListView(
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
            ArchivedAccountsListView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = transferScreen,
            onArrowBackClick = onArrowBackClick
        ) {
            AccountTransferView(onArrowBackClick = onArrowBackClick)
        }
    }
}
