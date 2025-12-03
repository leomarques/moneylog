package lmm.moneylog.ui.components.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

const val HOME_SCREEN = "home"
const val TRANSACTIONS_LIST_SCREEN = "transactions_list"
const val SETTINGS_SCREEN = "settings"
const val PARAM_TYPE_ALL = "all"

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
