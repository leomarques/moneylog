package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R
import lmm.moneylog.ui.components.bottombar.AppNavigationBar
import lmm.moneylog.ui.components.bottombar.mocks.BottomBarPreviewData
import lmm.moneylog.ui.theme.AppTheme

/**
 * Main UI entry point for the application
 * Contains the app theme, scaffold with bottom navigation, and home screen content
 *
 * @param modifier Modifier for the main container
 */
@Composable
fun MainUI(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    AppNavigationBar(
                        items =
                            BottomBarPreviewData.sampleBottomBarItemsWithSelection(
                                iconOne =
                                    ImageVector.vectorResource(
                                        id = R.drawable.outline_receipt_long_24
                                    )
                            ),
                        onClick = BottomBarPreviewData.sampleOnClick
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
    }
}

@Preview
@Composable
private fun MainUIPreview() {
    MainUI(content = { Text("Hello, World!") })
}
