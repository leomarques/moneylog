package lmm.moneylog.ui.features.category

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import lmm.moneylog.ui.misc.clickFab
import lmm.moneylog.ui.misc.navigateToCategories
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.junit.Rule
import org.junit.Test

class AddCategoryTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldAddCategory() {
        with(composeTestRule) {
            navigateToCategories()
            clickFab()

            onNodeWithTag("CategoryNameTextField").performTextInput("Lorem")
            clickFab()

            onNodeWithText("Lorem").assertIsDisplayed()
        }
    }
}
