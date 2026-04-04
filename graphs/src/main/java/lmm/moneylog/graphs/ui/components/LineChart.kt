package lmm.moneylog.graphs.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lmm.moneylog.data.graphs.model.NetWorthPoint
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.theme.Size as ThemeSize

private const val CHART_PADDING_RATIO = 0.1
private const val CHART_HEIGHT_RATIO = 0.8f
private const val MONTH_LABEL_INTERVAL = 3
private const val GRID_LINES_STEPS = 4
private const val GRID_LINE_ALPHA = 0.2f
private val GRID_LINE_COLOR = Color.Gray.copy(alpha = GRID_LINE_ALPHA)
private val ZERO_LINE_COLOR = Color.Gray.copy(alpha = 0.5f)
private const val DASH_ON = 10f
private const val DASH_OFF = 10f
private val DASH_PATTERN = floatArrayOf(DASH_ON, DASH_OFF)
private const val DOT_RADIUS_DP = 4
private const val LINE_WIDTH_DP = 3
private const val ZERO_LINE_WIDTH_DP = 2
private const val Y_RATIO_OFFSET = 0.9f
private const val Y_RATIO_START = 0.1f

private data class ChartScaleInfo(
    val scaleY: Float,
    val scaleX: Float,
    val paddedMin: Double,
    val canvasHeight: Float
)

private fun calculateScaleInfo(
    data: List<NetWorthPoint>,
    canvasWidth: Float,
    canvasHeight: Float
): ChartScaleInfo {
    val minValue = data.minOf { it.netWorth }
    val maxValue = data.maxOf { it.netWorth }
    val valueRange = maxValue - minValue

    val paddedMin = minValue - (valueRange * CHART_PADDING_RATIO)
    val paddedMax = maxValue + (valueRange * CHART_PADDING_RATIO)
    val paddedRange = paddedMax - paddedMin

    val scaleY = if (paddedRange > 0) (canvasHeight * CHART_HEIGHT_RATIO) / paddedRange.toFloat() else 1f
    val scaleX = canvasWidth / (data.size - 1).toFloat()

    return ChartScaleInfo(scaleY, scaleX, paddedMin, canvasHeight)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawGridLines(
    canvasWidth: Float,
    canvasHeight: Float
) {
    val steps = GRID_LINES_STEPS
    for (i in 0..steps) {
        val y = canvasHeight * Y_RATIO_START + (canvasHeight * CHART_HEIGHT_RATIO * i / steps)
        drawLine(
            color = GRID_LINE_COLOR,
            start = Offset(0f, y),
            end = Offset(canvasWidth, y),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(DASH_PATTERN)
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawZeroLine(
    minValue: Double,
    maxValue: Double,
    scaleInfo: ChartScaleInfo,
    canvasWidth: Float
) {
    if (minValue < 0 && maxValue > 0) {
        val zeroY =
            scaleInfo.canvasHeight * Y_RATIO_OFFSET - ((0 - scaleInfo.paddedMin) * scaleInfo.scaleY).toFloat()
        drawLine(
            color = ZERO_LINE_COLOR,
            start = Offset(0f, zeroY),
            end = Offset(canvasWidth, zeroY),
            strokeWidth = ZERO_LINE_WIDTH_DP.dp.toPx()
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawLinePath(
    data: List<NetWorthPoint>,
    scaleInfo: ChartScaleInfo,
    canvasHeight: Float,
    lineColor: Color
) {
    val path = Path()
    data.forEachIndexed { index, point ->
        val x = index * scaleInfo.scaleX
        val y =
            canvasHeight * Y_RATIO_OFFSET - ((point.netWorth - scaleInfo.paddedMin) * scaleInfo.scaleY).toFloat()

        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    drawPath(
        path = path,
        color = lineColor,
        style =
            Stroke(
                width = LINE_WIDTH_DP.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
    )

    data.forEachIndexed { index, point ->
        val x = index * scaleInfo.scaleX
        val y =
            canvasHeight * Y_RATIO_OFFSET - ((point.netWorth - scaleInfo.paddedMin) * scaleInfo.scaleY).toFloat()
        drawCircle(
            color = lineColor,
            radius = DOT_RADIUS_DP.dp.toPx(),
            center = Offset(x, y)
        )
    }
}

/**
 * A line chart component displaying net worth over time
 *
 * @param data List of NetWorthPoint to display in the line chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun LineChart(
    data: List<NetWorthPoint>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(lmm.moneylog.graphs.R.string.graphs_no_data),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    val lineColor = Color(0xFF2196F3) // Blue
    val positiveColor = Color(0xFF4CAF50) // Green
    val negativeColor = Color(0xFFF44336) // Red

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ThemeSize.MediumSpaceSize)
    ) {
        // Chart title
        Text(
            text = stringResource(lmm.moneylog.graphs.R.string.graphs_line_chart_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Line Chart
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(horizontal = ThemeSize.DefaultSpaceSize)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            if (data.size < 2) return@Canvas

            val scaleInfo = calculateScaleInfo(data, canvasWidth, canvasHeight)
            val minValue = data.minOf { it.netWorth }
            val maxValue = data.maxOf { it.netWorth }

            // Draw grid lines (horizontal)
            drawGridLines(canvasWidth, canvasHeight)

            // Draw zero line if data crosses zero
            drawZeroLine(minValue, maxValue, scaleInfo, canvasWidth)

            // Draw line path
            drawLinePath(data, scaleInfo, canvasHeight, lineColor)
        }

        // Month labels (show every 3rd month to avoid crowding)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data
                .filterIndexed { index, _ -> index % MONTH_LABEL_INTERVAL == 0 || index == data.size - 1 }
                .forEach { point ->
                    Text(
                        text = point.monthName,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
        }

        // Summary statistics - show current net worth only
        val currentNetWorth = data.lastOrNull()?.netWorth ?: 0.0

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ThemeSize.SmallSpaceSize)
        ) {
            Text(
                text = stringResource(lmm.moneylog.graphs.R.string.graphs_line_chart_current_net_worth),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = currentNetWorth.formatForRs(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if (currentNetWorth >= 0) positiveColor else negativeColor
            )
        }

        // Monthly values list
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            verticalArrangement = Arrangement.spacedBy(ThemeSize.XSmallSpaceSize)
        ) {
            Text(
                text = stringResource(lmm.moneylog.graphs.R.string.graphs_line_chart_monthly_history),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = ThemeSize.SmallSpaceSize)
            )

            // Create indexed data for variance calculation
            val indexedData = data.withIndex().toList().reversed()

            indexedData.forEach { (index, point) ->
                val pointColor = if (point.netWorth >= 0) positiveColor else negativeColor

                // Calculate variance from previous month (index - 1 in chronological order)
                val prevNetWorth = if (index > 0) data[index - 1].netWorth else point.netWorth
                val variance = calculateNetWorthVariance(point.netWorth, prevNetWorth)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = point.monthName.take(3) + "/" + point.year,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formatNetWorthWithVariance(point.netWorth, variance),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = pointColor,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

private fun calculateNetWorthVariance(current: Double, previous: Double): Double =
    if (previous != 0.0) {
        ((current - previous) / kotlin.math.abs(previous)) * 100
    } else {
        0.0
    }

private fun formatNetWorthWithVariance(value: Double, variance: Double): String {
    val varianceStr = String.format(java.util.Locale.getDefault(), "(%+.1f%%)", variance)
    return "$varianceStr ${value.formatForRs()}"
}
