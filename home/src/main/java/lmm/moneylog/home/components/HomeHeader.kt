package lmm.moneylog.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Displays a centered header text for the home screen within a card
 *
 * @param text The text to display in the header (e.g., period title)
 * @param modifier Modifier for the card container
 * @param valuesMasked Whether values are currently masked
 * @param onMaskToggle Callback invoked when the mask toggle icon is clicked
 */
@Composable
fun HomeHeader(
    text: String,
    modifier: Modifier = Modifier,
    valuesMasked: Boolean = false,
    onMaskToggle: () -> Unit = {}
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
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onMaskToggle,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (valuesMasked) Icons.Default.Lock else Icons.Default.LockOpen,
                    contentDescription = if (valuesMasked) "Unlock values" else "Lock values",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
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
            modifier = Modifier.fillMaxWidth(),
            valuesMasked = false
        )
    }
}
