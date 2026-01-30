package lmm.moneylog.ui.navigation.navhost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import lmm.moneylog.ui.navigation.misc.ABOUT_SCREEN
import lmm.moneylog.ui.navigation.misc.ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.ACCOUNT_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.ARCHIVED_ACCOUNTS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORIES_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.CATEGORY_KEYWORDS_SCREEN
import lmm.moneylog.ui.navigation.misc.CREDITCARD_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.CREDITCARD_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.GRAPHS_SCREEN
import lmm.moneylog.ui.navigation.misc.INVOICE_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.NOTIFICATION_SETTINGS_SCREEN
import lmm.moneylog.ui.navigation.misc.PARAM_CARD_ID
import lmm.moneylog.ui.navigation.misc.PARAM_ID
import lmm.moneylog.ui.navigation.misc.PARAM_INVOICE_CODE
import lmm.moneylog.ui.navigation.misc.PARAM_IS_INCOME
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_ALL
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTION_DETAIL_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSFER_SCREEN

@Composable
fun NavHostParams(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    onNavigate: (destination: String) -> Unit,
    onBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    MyNavHost(
        navController = navController,
        startDestination = startDestination,
        onHomeFabClick = {
            onNavigate(TRANSACTION_DETAIL_SCREEN)
        },
        onBalanceCardClick = { typeOfValue ->
            if (typeOfValue == PARAM_TYPE_ALL) {
                onNavigate(ACCOUNTS_LIST_SCREEN)
            } else {
                onNavigate("$TRANSACTIONS_LIST_SCREEN/$typeOfValue")
            }
        },
        onArrowBackClick = onBackNavigation,
        onTransactionsFabClick = {
            onNavigate(TRANSACTION_DETAIL_SCREEN)
        },
        onTransactionsItemClick = { id ->
            onNavigate("$TRANSACTION_DETAIL_SCREEN?$PARAM_ID=$id")
        },
        onAccountsFabClick = {
            onNavigate(ACCOUNT_DETAIL_SCREEN)
        },
        onAccountsItemClick = { id ->
            onNavigate("$ACCOUNT_DETAIL_SCREEN?$PARAM_ID=$id")
        },
        onCategoriesFabClick = { isIncome ->
            onNavigate("$CATEGORY_DETAIL_SCREEN?$PARAM_IS_INCOME=$isIncome")
        },
        onCategoriesItemClick = { id ->
            onNavigate("$CATEGORY_DETAIL_SCREEN?$PARAM_ID=$id")
        },
        onArchivedIconClick = {
            onNavigate(ARCHIVED_ACCOUNTS_LIST_SCREEN)
        },
        onTransferIconClick = {
            onNavigate(TRANSFER_SCREEN)
        },
        onCreditCardClick = { cardId, invoiceCode ->
            onNavigate("$INVOICE_LIST_SCREEN?$PARAM_CARD_ID=$cardId&$PARAM_INVOICE_CODE=$invoiceCode")
        },
        onCreditCardsFabClick = {
            onNavigate(CREDITCARD_DETAIL_SCREEN)
        },
        onInvoiceListFabClick = { cardId ->
            onNavigate("$TRANSACTION_DETAIL_SCREEN?$PARAM_CARD_ID=$cardId")
        },
        onCreditCardsItemClick = { id ->
            onNavigate("$CREDITCARD_DETAIL_SCREEN?$PARAM_ID=$id")
        },
        modifier = modifier.padding(paddingValues),
        onNotificationSettingsClick = {
            onNavigate(NOTIFICATION_SETTINGS_SCREEN)
        },
        onCategoryKeywordsClick = {
            onNavigate(CATEGORY_KEYWORDS_SCREEN)
        },
        onAccountsClick = {
            onNavigate(ACCOUNTS_LIST_SCREEN)
        },
        onCategoriesClick = {
            onNavigate(CATEGORIES_LIST_SCREEN)
        },
        onCreditCardsClick = {
            onNavigate(CREDITCARD_LIST_SCREEN)
        },
        onGraphsClick = {
            onNavigate(GRAPHS_SCREEN)
        },
        onAboutClick = {
            onNavigate(ABOUT_SCREEN)
        }
    )
}
