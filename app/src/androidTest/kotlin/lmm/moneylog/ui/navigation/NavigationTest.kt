package lmm.moneylog.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import lmm.moneylog.ui.misc.navigateToAccounts
import lmm.moneylog.ui.misc.navigateToCategories
import lmm.moneylog.ui.misc.navigateToTransactions
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldShowHomeScreen() {
        composeTestRule.onNodeWithTag("HomeScreen").assertIsDisplayed()
    }

    @Test
    fun shouldShowTransactionsListWhenClickNavigationBarItem1() {
        composeTestRule.navigateToTransactions()
        composeTestRule.onNodeWithTag("TransactionsListScreen").assertIsDisplayed()
    }

    @Test
    fun shouldShowAccountsListWhenClickNavigationBarItem2() {
        composeTestRule.navigateToAccounts()
        composeTestRule.onNodeWithTag("AccountsListScreen").assertIsDisplayed()
    }

    @Test
    fun shouldShowCategoriesListWhenClickNavigationBarItem3() {
        composeTestRule.navigateToCategories()
        composeTestRule.onNodeWithTag("CategoriesListScreen").assertIsDisplayed()
    }
}
