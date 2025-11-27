package lmm.moneylog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.bottombar.BottomBar
import lmm.moneylog.ui.screens.home.layout.HomeLayout
import lmm.moneylog.ui.screens.home.mocks.HomePreviewData
import lmm.moneylog.ui.theme.AppTheme

@Composable
fun MainUI(modifier: Modifier = Modifier) {
    AppTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        selectedIndex = 0,
                        onNavigate = { }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                    ) {
                        HomeLayout(
                            data = HomePreviewData.sampleHomeScreenData(),
                            callbacks = HomePreviewData.sampleHomeLayoutCallbacks()
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun MainUIPreview() {
    MainUI()
}
