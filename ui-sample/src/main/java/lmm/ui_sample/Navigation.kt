package lmm.ui_sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lmm.moneylog.home.ui.HomeLayout
import lmm.moneylog.home.ui.HomeLayoutCallbacks
import lmm.moneylog.ui.components.misc.ContentNavBar

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    ContentNavBar(
        onNavBarClick = { index ->
            // Handle navigation bar click here
        },
        modifier = modifier
    ) {
        HomeLayout(callbacks = HomeLayoutCallbacks())
    }
}
