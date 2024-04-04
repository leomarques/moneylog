package lmm.moneylog.ui.misc

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import lmm.moneylog.ui.navigation.misc.MainActivity

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.navigateToTransactions() {
    onNodeWithTag("BaseNavigationBarItem1").performClick()
}

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.navigateToAccounts() {
    onNodeWithTag("BaseNavigationBarItem2").performClick()
}

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.navigateToCategories() {
    onNodeWithTag("BaseNavigationBarItem3").performClick()
}

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clickFab() {
    onNodeWithTag("Fab").performClick()
}
