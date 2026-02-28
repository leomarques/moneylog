package lmm.moneylog.ui.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {
    private val _currentTab = MutableStateFlow(HOME_INDEX)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _showBottomBar = MutableStateFlow(true)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar.asStateFlow()

    fun onDestinationChanged(route: String?) {
        route?.let {
            _currentTab.value = it.toTabIndex()
            _showBottomBar.value = it.shouldShowBottomBar()
        }
    }
}

fun String.toTabIndex(): Int {
    val baseRoute = this.split("/", "?")[0]
    return when (baseRoute) {
        HOME_SCREEN -> HOME_INDEX
        in transactionScreens() -> TRANSACTIONS_INDEX
        in settingsScreens() -> SETTINGS_INDEX
        else -> HOME_INDEX
    }
}

fun String.shouldShowBottomBar(): Boolean {
    val baseRoute = this.split("/", "?")[0]
    return baseRoute in screensWithBottomNav()
}

private fun transactionScreens() =
    setOf(
        TRANSACTIONS_LIST_SCREEN,
        TRANSACTION_DETAIL_SCREEN,
        INVOICE_LIST_SCREEN
    )

private fun settingsScreens() =
    setOf(
        SETTINGS_SCREEN,
        ACCOUNTS_LIST_SCREEN,
        ACCOUNT_DETAIL_SCREEN,
        ARCHIVED_ACCOUNTS_LIST_SCREEN,
        CREDITCARD_LIST_SCREEN,
        CREDITCARD_DETAIL_SCREEN,
        CATEGORIES_LIST_SCREEN,
        CATEGORY_DETAIL_SCREEN,
        NOTIFICATION_SETTINGS_SCREEN,
        CATEGORY_KEYWORDS_SCREEN,
        GRAPHS_SCREEN,
        TRANSFER_SCREEN,
        TRANSFERS_LIST_SCREEN
    )

private fun screensWithBottomNav() =
    setOf(
        HOME_SCREEN,
        TRANSACTIONS_LIST_SCREEN,
        SETTINGS_SCREEN
    )
