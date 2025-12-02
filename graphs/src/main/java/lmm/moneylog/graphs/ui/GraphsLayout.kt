package lmm.moneylog.graphs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Main graphs screen layout
 * Placeholder for future graph implementations
 *
 * @param modifier Modifier for the screen container
 */
@Composable
fun GraphsLayout(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(Size.DefaultSpaceSize),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Graphs Module - Coming Soon",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GraphsLayoutPreview() {
    AppTheme {
        GraphsLayout()
    }
}
