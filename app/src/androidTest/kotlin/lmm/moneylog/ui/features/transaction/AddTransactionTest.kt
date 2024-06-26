package lmm.moneylog.ui.features.transaction

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import lmm.moneylog.ui.misc.clickFab
import lmm.moneylog.ui.misc.navigateToTransactions
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.junit.Rule
import org.junit.Test

class AddTransactionTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldAddTransaction() {
        with(composeTestRule) {
            clickFab()

            onNodeWithTag("ValueTextField").performTextInput("100")
            clickFab()

            onAllNodesWithText("R$100,00").assertCountEquals(2)
            navigateToTransactions()

            onNodeWithText("R$100,00").assertIsDisplayed()
        }
    }
}
