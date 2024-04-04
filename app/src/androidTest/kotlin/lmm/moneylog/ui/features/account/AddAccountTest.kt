package lmm.moneylog.ui.features.account

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import lmm.moneylog.ui.misc.clickFab
import lmm.moneylog.ui.misc.navigateToAccounts
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.junit.Rule
import org.junit.Test

class AddAccountTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldAddAccount() {
        with(composeTestRule) {
            navigateToAccounts()
            clickFab()

            onNodeWithTag("AccountNameTextField").performTextInput("Lorem")
            clickFab()

            onNodeWithText("Lorem").assertIsDisplayed()
        }
    }
}
