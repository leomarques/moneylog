package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.AppTheme

/**
 * Main UI entry point for the application
 * Contains the app theme, scaffold with bottom navigation, and home screen content
 *
 * @param modifier Modifier for the main container
 */
@Composable
fun MainComposable(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun MainComposablePreview() {
    MainComposable(content = { Text("Hello, World!") })
}
