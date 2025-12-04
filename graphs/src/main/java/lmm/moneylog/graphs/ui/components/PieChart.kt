package lmm.moneylog.graphs.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lmm.moneylog.data.graphs.model.CategoryAmount
import lmm.moneylog.graphs.R
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.theme.neutralColor
import java.util.Locale
import lmm.moneylog.ui.theme.Size as ThemeSize

/**
 * A simple pie chart component that displays category amounts
 *
 * @param data List of CategoryAmount to display in the pie chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun PieChart(
    data: List<CategoryAmount>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_data),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ThemeSize.MediumSpaceSize)
    ) {
        // Pie chart
        Canvas(
            modifier =
                Modifier
                    .size(280.dp)
                    .padding(ThemeSize.DefaultSpaceSize)
        ) {
            val canvasSize = size.minDimension
            val chartSize = canvasSize * 0.9f
            val topLeft =
                Offset(
                    (size.width - chartSize) / 2,
                    (size.height - chartSize) / 2
                )

            var startAngle = -90f

            data.forEach { categoryAmount ->
                val sweepAngle = (categoryAmount.percentage / 100f) * 360f
                val color =
                    categoryAmount.categoryColor?.toComposeColor()
                        ?: neutralColor

                // Draw filled arc (pie slice)
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = topLeft,
                    size = Size(chartSize, chartSize)
                )

                startAngle += sweepAngle
            }
        }

        // Legend
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            verticalArrangement = Arrangement.spacedBy(ThemeSize.SmallSpaceSize)
        ) {
            data.forEach { categoryAmount ->
                LegendItem(
                    categoryAmount = categoryAmount
                )
            }
        }
    }
}

/**
 * Legend item showing category color, name, amount, and percentage
 */
@Composable
private fun LegendItem(
    categoryAmount: CategoryAmount,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ThemeSize.SmallSpaceSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color indicator
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(
                color =
                    categoryAmount.categoryColor?.let { Color(it.toULong()) }
                        ?: neutralColor
            )
        }

        // Category name
        Text(
            text = categoryAmount.categoryName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        // Amount and percentage
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = categoryAmount.totalAmount.formatForRs(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = String.format(Locale.getDefault(), "%.1f%%", categoryAmount.percentage),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
