package lmm.moneylog.ui.features.creditcard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import lmm.moneylog.ui.misc.clickFab
import lmm.moneylog.ui.misc.navigateToCreditCards
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.junit.Rule
import org.junit.Test

class AddCreditCardTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldAddCreditCard() {
        with(composeTestRule) {
            navigateToCreditCards() // Assuming this navigation function exists
            clickFab()

            // Assuming the tag for the name input field is "CreditCardNameTextField"
            onNodeWithTag("CreditCardNameTextField").performTextInput("My New Card")
            clickFab()

            // Assuming the added card's name is displayed as text
            onNodeWithText("My New Card").assertIsDisplayed()
        }
    }
}
