package lmm.moneylog.ui.navigation.navhost

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import lmm.moneylog.home.ui.HomeLayout
import lmm.moneylog.home.ui.HomeLayoutCallbacks
import lmm.moneylog.home.ui.cards.CreditCardsCardCallbacks
import lmm.moneylog.home.ui.cards.TotalBalanceCardCallbacks
import lmm.moneylog.ui.features.account.archive.view.layout.ArchivedAccountsListView
import lmm.moneylog.ui.features.account.detail.view.layout.AccountDetailView
import lmm.moneylog.ui.features.account.list.view.layout.AccountsListView
import lmm.moneylog.ui.features.account.transfer.view.layout.AccountTransferView
import lmm.moneylog.ui.features.category.detail.view.layout.CategoryDetailView
import lmm.moneylog.ui.features.category.list.view.layouts.CategoriesListView
import lmm.moneylog.ui.features.categorykeywords.CategoryKeywordsScreen
import lmm.moneylog.ui.features.creditcard.detail.view.layout.CreditCardDetailView
import lmm.moneylog.ui.features.creditcard.list.view.layouts.CreditCardsListView
import lmm.moneylog.ui.features.graphs.GraphsScreen
import lmm.moneylog.ui.features.invoice.view.InvoiceListView
import lmm.moneylog.ui.features.notification.settings.view.NotificationSettingsScreen
import lmm.moneylog.ui.features.settings.SettingsScreen
import lmm.moneylog.ui.features.transaction.detail.view.layout.TransactionDetailView
import lmm.moneylog.ui.features.transaction.list.view.TransactionsListView
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_INCOME
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_OUTCOME
import lmm.moneylog.ui.features.transfer.list.view.TransfersListView
import lmm.moneylog.ui.navigation.misc.ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.ACCOUNT_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.ARCHIVED_ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORIES_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_KEYWORDS_SCREEN
import lmm.moneylog.ui.navigation.misc.CREDITCARD_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.CREDITCARD_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.GRAPHS_SCREEN
import lmm.moneylog.ui.navigation.misc.HOME_SCREEN
import lmm.moneylog.ui.navigation.misc.INVOICE_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.NOTIFICATION_SETTINGS_SCREEN
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE
import lmm.moneylog.ui.navigation.misc.PARAM_IS_INCOME
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_ALL
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_OF_VALUE
import lmm.moneylog.ui.navigation.misc.SETTINGS_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTION_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSFER_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSFERS_LIST_SCREEN
import lmm.moneylog.ui.navigation.NavigationActions
import lmm.moneylog.ui.navigation.misc.composableExt
import lmm.moneylog.ui.navigation.misc.slideInFromRight
import lmm.moneylog.ui.navigation.misc.slideOutToRight
import lmm.moneylog.ui.navigation.misc.toTransactionType

@Composable
fun MyNavHost(
    navController: NavHostController,
    startDestination: String,
    actions: NavigationActions,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = startDestination
    ) {
        val transactionArguments =
            listOf(
                navArgument(PARAM_ID) {
                    defaultValue = -1L
                    type = NavType.LongType
                }
            )

        val intIdArguments =
            listOf(
                navArgument(PARAM_ID) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )

        composable(
            route = HOME_SCREEN,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            HomeLayout(
                callbacks =
                    HomeLayoutCallbacks(
                        balanceCardCallbacks =
                            TotalBalanceCardCallbacks(
                                onCardClick = { actions.onBalanceCardClick(PARAM_TYPE_ALL) }
                            ),
                        onIncomeClick = { actions.onBalanceCardClick(GET_TRANSACTIONS_INCOME) },
                        onExpensesClick = { actions.onBalanceCardClick(GET_TRANSACTIONS_OUTCOME) },
                        creditCardsCallbacks =
                            CreditCardsCardCallbacks(
                                onCardClick = { creditCard ->
                                    actions.onCreditCardClick(creditCard.cardId, creditCard.invoiceCode)
                                }
                            ),
                        onExpensesCardClick = actions::onGraphsClick,
                        onFabClick = actions::onHomeFabClick
                    )
            )
        }

        composableExt(
            route = "$TRANSACTIONS_LIST_SCREEN/{$PARAM_TYPE_OF_VALUE}",
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) { backStackEntry ->
            val param = backStackEntry?.arguments?.getString(PARAM_TYPE_OF_VALUE)

            TransactionsListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onTransactionsFabClick,
                typeOfValue = param.toTransactionType(),
                onItemClick = actions::onTransactionsItemClick
            )
        }

        composableExt(
            route = "$TRANSACTION_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}&$PARAM_CARD_ID={$PARAM_CARD_ID}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments = transactionArguments,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            TransactionDetailView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = ACCOUNTS_LIST_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            AccountsListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onAccountsFabClick,
                onItemClick = actions::onAccountsItemClick,
                onArchivedIconClick = actions::onArchivedIconClick,
                onTransferIconClick = actions::onTransferIconClick
            )
        }

        composableExt(
            route = "$ACCOUNT_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments = intIdArguments,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            AccountDetailView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = CATEGORIES_LIST_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CategoriesListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onCategoriesFabClick,
                onItemClick = actions::onCategoriesItemClick
            )
        }

        composableExt(
            route = "$CATEGORY_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}&$PARAM_IS_INCOME={$PARAM_IS_INCOME}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments =
                intIdArguments +
                    listOf(
                        navArgument(PARAM_IS_INCOME) {
                            defaultValue = true
                            type = NavType.BoolType
                        }
                    ),
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CategoryDetailView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = ARCHIVED_ACCOUNTS_LIST_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            ArchivedAccountsListView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = TRANSFER_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            AccountTransferView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = TRANSFERS_LIST_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            TransfersListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onTransferIconClick,
                onItemClick = { actions.onViewTransfersListClick() }
            )
        }

        composableExt(
            route = CREDITCARD_LIST_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CreditCardsListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onCreditCardsFabClick,
                onItemClick = actions::onCreditCardsItemClick
            )
        }

        composableExt(
            route = "$CREDITCARD_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments = intIdArguments,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CreditCardDetailView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = "$INVOICE_LIST_SCREEN?$PARAM_CARD_ID={$PARAM_CARD_ID}&$PARAM_INVOICE_CODE={$PARAM_INVOICE_CODE}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments = intIdArguments,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            InvoiceListView(
                onArrowBackClick = actions::onArrowBackClick,
                onFabClick = actions::onInvoiceListFabClick,
                onItemClick = actions::onTransactionsItemClick
            )
        }

        composableExt(
            route = "$CREDITCARD_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = actions::onArrowBackClick,
            arguments = intIdArguments,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CreditCardDetailView(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = NOTIFICATION_SETTINGS_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            NotificationSettingsScreen(
                onArrowBackClick = actions::onArrowBackClick,
                onCategoryKeywordsClick = actions::onCategoryKeywordsClick
            )
        }

        composableExt(
            route = CATEGORY_KEYWORDS_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            CategoryKeywordsScreen(onArrowBackClick = actions::onArrowBackClick)
        }

        composableExt(
            route = SETTINGS_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            SettingsScreen(
                onArrowBackClick = actions::onArrowBackClick,
                onAccountsClick = actions::onAccountsClick,
                onCategoriesClick = actions::onCategoriesClick,
                onCreditCardsClick = actions::onCreditCardsClick,
                onTransfersListClick = actions::onViewTransfersListClick,
                onNotificationSettingsClick = actions::onNotificationSettingsClick,
                onGraphsClick = actions::onGraphsClick
            )
        }

        composableExt(
            route = GRAPHS_SCREEN,
            onArrowBackClick = actions::onArrowBackClick,
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutToRight() }
        ) {
            GraphsScreen(onArrowBackClick = actions::onArrowBackClick)
        }
    }
}
