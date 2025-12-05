package lmm.moneylog.home.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.home.R
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.home.viewmodels.PeriodInfo
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Displays a centered header text for the home screen within a card
 *
 * @param periodInfo The period information (month name and year) to display
 * @param modifier Modifier for the card container
 * @param valuesMasked Whether values are currently masked
 * @param onMaskToggle Callback invoked when the mask toggle icon is clicked
 */
@Composable
fun HomeHeader(
    periodInfo: PeriodInfo?,
    modifier: Modifier = Modifier,
    valuesMasked: Boolean = false,
    onMaskToggle: () -> Unit = {}
) {
    val text = periodInfo?.let { "${it.monthName} ${it.year}" } ?: ""

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
                    .padding(Size.SmallSpaceSize2)
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
                    contentDescription =
                        stringResource(
                            if (valuesMasked) R.string.unlock_values else R.string.lock_values
                        ),
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
            periodInfo = HomePreviewData.SAMPLE_PERIOD_INFO,
            modifier = Modifier.fillMaxWidth(),
            valuesMasked = false
        )
    }
}
