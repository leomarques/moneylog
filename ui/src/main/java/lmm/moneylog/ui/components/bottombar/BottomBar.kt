package lmm.moneylog.ui.components.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomBar(
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onNavigate: (destination: String) -> Unit = {}
) {
    MyNavigationBar(
        selectedIndex = selectedIndex,
        modifier = modifier,
        onClick = { index ->
            when (index) {
                0 -> onNavigate(HOME_SCREEN)
                1 -> onNavigate("$TRANSACTIONS_LIST_SCREEN/$PARAM_TYPE_ALL")
                2 -> onNavigate(ACCOUNTS_LIST_SCREEN)
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
