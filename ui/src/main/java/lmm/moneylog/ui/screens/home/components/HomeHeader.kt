package lmm.moneylog.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.screens.home.mocks.HomePreviewData
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Displays a centered header text for the home screen
 */
@Composable
fun HomeHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = HomeHeaderDefaults.CARD_ELEVATION
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.DefaultSpaceSize),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

object HomeHeaderDefaults {
    val CARD_ELEVATION = 2.dp
}

@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    AppTheme {
        HomeHeader(
            text = HomePreviewData.SAMPLE_PERIOD_TITLE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
