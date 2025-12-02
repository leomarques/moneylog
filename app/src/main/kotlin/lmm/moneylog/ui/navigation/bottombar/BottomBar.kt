package lmm.moneylog.ui.navigation.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.navigation.misc.HOME_SCREEN
import lmm.moneylog.ui.navigation.misc.PARAM_TYPE_ALL
import lmm.moneylog.ui.navigation.misc.SETTINGS_SCREEN
import lmm.moneylog.ui.navigation.misc.TRANSACTIONS_LIST_SCREEN

@Composable
fun BottomBar(
    selectedIndex: Int,
    onNavigate: (destination: String) -> Unit
) {
    MyNavigationBar(
        selectedIndex = selectedIndex,
        onClick = { index ->
            when (index) {
                0 -> onNavigate(HOME_SCREEN)
                1 -> onNavigate("$TRANSACTIONS_LIST_SCREEN/$PARAM_TYPE_ALL")
                2 -> onNavigate(SETTINGS_SCREEN)
            }
        }
    )
}

@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(
        selectedIndex = 0,
        onNavigate = {}
    )
}
