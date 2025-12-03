package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.bottombar.MyNavigationBar

@Composable
fun ContentNavBar(
    onNavBarClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            MyNavigationBar(
                selectedIndex = selectedIndex,
                onClick = { index ->
                    selectedIndex = index
                    onNavBarClick(index)
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
            ) {
                content()
            }
        }
    )
}

@Preview
@Composable
private fun ContentNavBarPreview() {
    ContentNavBar(onNavBarClick = {}) {}
}
