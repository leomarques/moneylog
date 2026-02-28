package lmm.moneylog.ui.navigation

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
import lmm.moneylog.ui.navigation.misc.TRANSFERS_LIST_SCREEN

@Suppress("TooManyFunctions")
interface NavigationActions {
    fun onHomeFabClick()
    fun onBalanceCardClick(typeOfValue: String)
    fun onArrowBackClick()
    fun onTransactionsFabClick()
    fun onTransactionsItemClick(id: Long)
    fun onAccountsFabClick()
    fun onAccountsItemClick(id: Int)
    fun onCategoriesFabClick(isIncome: Boolean)
    fun onCategoriesItemClick(id: Int)
    fun onArchivedIconClick()
    fun onTransferIconClick()
    fun onCreditCardClick(cardId: Int, invoiceCode: String)
    fun onCreditCardsFabClick()
    fun onInvoiceListFabClick(cardId: Int)
    fun onCreditCardsItemClick(id: Int)
    fun onNotificationSettingsClick()
    fun onCategoryKeywordsClick()
    fun onAccountsClick()
    fun onCategoriesClick()
    fun onCreditCardsClick()
    fun onGraphsClick()
    fun onViewTransfersListClick()
}

fun createNavigationActions(
    onNavigate: (String) -> Unit,
    onBackNavigation: () -> Unit
): NavigationActions = object : NavigationActions {
    override fun onHomeFabClick() {
        onNavigate(TRANSACTION_DETAIL_SCREEN)
    }

    override fun onBalanceCardClick(typeOfValue: String) {
        if (typeOfValue == PARAM_TYPE_ALL) {
            onNavigate(ACCOUNTS_LIST_SCREEN)
        } else {
            onNavigate("$TRANSACTIONS_LIST_SCREEN/$typeOfValue")
        }
    }

    override fun onArrowBackClick() = onBackNavigation()

    override fun onTransactionsFabClick() {
        onNavigate(TRANSACTION_DETAIL_SCREEN)
    }

    override fun onTransactionsItemClick(id: Long) {
        onNavigate("$TRANSACTION_DETAIL_SCREEN?$PARAM_ID=$id")
    }

    override fun onAccountsFabClick() {
        onNavigate(ACCOUNT_DETAIL_SCREEN)
    }

    override fun onAccountsItemClick(id: Int) {
        onNavigate("$ACCOUNT_DETAIL_SCREEN?$PARAM_ID=$id")
    }

    override fun onCategoriesFabClick(isIncome: Boolean) {
        onNavigate("$CATEGORY_DETAIL_SCREEN?$PARAM_IS_INCOME=$isIncome")
    }

    override fun onCategoriesItemClick(id: Int) {
        onNavigate("$CATEGORY_DETAIL_SCREEN?$PARAM_ID=$id")
    }

    override fun onArchivedIconClick() {
        onNavigate(ARCHIVED_ACCOUNTS_LIST_SCREEN)
    }

    override fun onTransferIconClick() {
        onNavigate(TRANSFER_SCREEN)
    }

    override fun onCreditCardClick(cardId: Int, invoiceCode: String) {
        onNavigate(
            "$INVOICE_LIST_SCREEN?" +
                "$PARAM_CARD_ID=$cardId&" +
                "$PARAM_INVOICE_CODE=$invoiceCode"
        )
    }

    override fun onCreditCardsFabClick() {
        onNavigate(CREDITCARD_DETAIL_SCREEN)
    }

    override fun onInvoiceListFabClick(cardId: Int) {
        onNavigate("$TRANSACTION_DETAIL_SCREEN?$PARAM_CARD_ID=$cardId")
    }

    override fun onCreditCardsItemClick(id: Int) {
        onNavigate("$CREDITCARD_DETAIL_SCREEN?$PARAM_ID=$id")
    }

    override fun onNotificationSettingsClick() {
        onNavigate(NOTIFICATION_SETTINGS_SCREEN)
    }

    override fun onCategoryKeywordsClick() {
        onNavigate(CATEGORY_KEYWORDS_SCREEN)
    }

    override fun onAccountsClick() {
        onNavigate(ACCOUNTS_LIST_SCREEN)
    }

    override fun onCategoriesClick() {
        onNavigate(CATEGORIES_LIST_SCREEN)
    }

    override fun onCreditCardsClick() {
        onNavigate(CREDITCARD_LIST_SCREEN)
    }

    override fun onGraphsClick() {
        onNavigate(GRAPHS_SCREEN)
    }

    override fun onViewTransfersListClick() {
        onNavigate(TRANSFERS_LIST_SCREEN)
    }
}
