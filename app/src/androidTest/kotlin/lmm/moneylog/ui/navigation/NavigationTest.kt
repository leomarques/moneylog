package lmm.moneylog.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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
        composeTestRule.onNodeWithTag("BaseNavigationBarItem1").performClick()
        composeTestRule.onNodeWithTag("TransactionsListScreen").assertIsDisplayed()
    }

    @Test
    fun shouldShowAccountsListWhenClickNavigationBarItem2() {
        composeTestRule.onNodeWithTag("BaseNavigationBarItem2").performClick()
        composeTestRule.onNodeWithTag("AccountsListScreen").assertIsDisplayed()
    }

    @Test
    fun shouldShowCategoriesListWhenClickNavigationBarItem3() {
        composeTestRule.onNodeWithTag("BaseNavigationBarItem3").performClick()
        composeTestRule.onNodeWithTag("CategoriesListScreen").assertIsDisplayed()
    }
}
