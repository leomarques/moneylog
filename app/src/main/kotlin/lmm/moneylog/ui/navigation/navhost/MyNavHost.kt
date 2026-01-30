package lmm.moneylog.ui.navigation.navhost

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
import lmm.moneylog.ui.features.about.AboutScreen
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import lmm.moneylog.BuildConfig
import lmm.moneylog.ui.features.transaction.detail.view.layout.TransactionDetailView
import lmm.moneylog.ui.features.transaction.list.view.TransactionsListView
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_INCOME
import lmm.moneylog.ui.features.transaction.list.viewmodel.GET_TRANSACTIONS_OUTCOME
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
import lmm.moneylog.ui.navigation.misc.ABOUT_SCREEN
import lmm.moneylog.ui.navigation.misc.SETTINGS_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTION_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSFER_SCREEN
import lmm.moneylog.ui.navigation.misc.composableExt
import lmm.moneylog.ui.navigation.misc.toTransactionType

@Composable
fun MyNavHost(
    navController: NavHostController,
    startDestination: String,
    onHomeFabClick: () -> Unit,
    onBalanceCardClick: (String) -> Unit,
    onArrowBackClick: () -> Unit,
    onTransactionsFabClick: () -> Unit,
    onTransactionsItemClick: (Long) -> Unit,
    onAccountsFabClick: () -> Unit,
    onAccountsItemClick: (Int) -> Unit,
    onCategoriesFabClick: (Boolean) -> Unit,
    onCategoriesItemClick: (Int) -> Unit,
    onArchivedIconClick: () -> Unit,
    onTransferIconClick: () -> Unit,
    onCreditCardClick: (Int, String) -> Unit,
    onCreditCardsFabClick: () -> Unit,
    onInvoiceListFabClick: (Int) -> Unit,
    onCreditCardsItemClick: (Int) -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onCategoryKeywordsClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onCreditCardsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onAboutClick: () -> Unit,
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

        composable(HOME_SCREEN) {
            HomeLayout(
                callbacks =
                    HomeLayoutCallbacks(
                        balanceCardCallbacks =
                            TotalBalanceCardCallbacks(
                                onCardClick = { onBalanceCardClick(PARAM_TYPE_ALL) }
                            ),
                        onIncomeClick = { onBalanceCardClick(GET_TRANSACTIONS_INCOME) },
                        onExpensesClick = { onBalanceCardClick(GET_TRANSACTIONS_OUTCOME) },
                        creditCardsCallbacks =
                            CreditCardsCardCallbacks(
                                onCardClick = { creditCard ->
                                    onCreditCardClick(creditCard.cardId, creditCard.invoiceCode)
                                }
                            ),
                        onExpensesCardClick = onGraphsClick,
                        onFabClick = onHomeFabClick
                    )
            )
        }

        composableExt(
            route = "$TRANSACTIONS_LIST_SCREEN/{$PARAM_TYPE_OF_VALUE}",
            onArrowBackClick = onArrowBackClick
        ) { backStackEntry ->
            val param = backStackEntry?.arguments?.getString(PARAM_TYPE_OF_VALUE)

            TransactionsListView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onTransactionsFabClick,
                typeOfValue = param.toTransactionType(),
                onItemClick = onTransactionsItemClick
            )
        }

        composableExt(
            route = "$TRANSACTION_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}&$PARAM_CARD_ID={$PARAM_CARD_ID}",
            onArrowBackClick = onArrowBackClick,
            arguments = transactionArguments
        ) {
            TransactionDetailView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = ACCOUNTS_LIST_SCREEN,
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
            route = "$ACCOUNT_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = onArrowBackClick,
            arguments = intIdArguments
        ) {
            AccountDetailView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = CATEGORIES_LIST_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            CategoriesListView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onCategoriesFabClick,
                onItemClick = onCategoriesItemClick
            )
        }

        composableExt(
            route = "$CATEGORY_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}&$PARAM_IS_INCOME={$PARAM_IS_INCOME}",
            onArrowBackClick = onArrowBackClick,
            arguments =
                intIdArguments +
                    listOf(
                        navArgument(PARAM_IS_INCOME) {
                            defaultValue = true
                            type = NavType.BoolType
                        }
                    )
        ) {
            CategoryDetailView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = ARCHIVED_ACCOUNTS_LIST_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            ArchivedAccountsListView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = TRANSFER_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            AccountTransferView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = CREDITCARD_LIST_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            CreditCardsListView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onCreditCardsFabClick,
                onItemClick = onCreditCardsItemClick
            )
        }

        composableExt(
            route = "$CREDITCARD_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = onArrowBackClick,
            arguments = intIdArguments
        ) {
            CreditCardDetailView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = "$INVOICE_LIST_SCREEN?$PARAM_CARD_ID={$PARAM_CARD_ID}&$PARAM_INVOICE_CODE={$PARAM_INVOICE_CODE}",
            onArrowBackClick = onArrowBackClick,
            arguments = intIdArguments
        ) {
            InvoiceListView(
                onArrowBackClick = onArrowBackClick,
                onFabClick = onInvoiceListFabClick,
                onItemClick = onTransactionsItemClick
            )
        }

        composableExt(
            route = "$CREDITCARD_DETAIL_SCREEN?$PARAM_ID={$PARAM_ID}",
            onArrowBackClick = onArrowBackClick,
            arguments = intIdArguments
        ) {
            CreditCardDetailView(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = NOTIFICATION_SETTINGS_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            NotificationSettingsScreen(
                onArrowBackClick = onArrowBackClick,
                onCategoryKeywordsClick = onCategoryKeywordsClick
            )
        }

        composableExt(
            route = CATEGORY_KEYWORDS_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            CategoryKeywordsScreen(onArrowBackClick = onArrowBackClick)
        }

        composableExt(
            route = SETTINGS_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            SettingsScreen(
                onArrowBackClick = onArrowBackClick,
                onAccountsClick = onAccountsClick,
                onCategoriesClick = onCategoriesClick,
                onCreditCardsClick = onCreditCardsClick,
                onNotificationSettingsClick = onNotificationSettingsClick,
                onGraphsClick = onGraphsClick,
                onAboutClick = onAboutClick
            )
        }

        composableExt(
            route = ABOUT_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            val context = LocalContext.current
            AboutScreen(
                onArrowBackClick = onArrowBackClick,
                appVersion = BuildConfig.VERSION_NAME,
                onPrivacyClick = {
                    Toast.makeText(context, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
                },
                onLicenseClick = {
                    Toast.makeText(context, "License clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }

        composableExt(
            route = GRAPHS_SCREEN,
            onArrowBackClick = onArrowBackClick
        ) {
            GraphsScreen(onArrowBackClick = onArrowBackClick)
        }
    }
}
