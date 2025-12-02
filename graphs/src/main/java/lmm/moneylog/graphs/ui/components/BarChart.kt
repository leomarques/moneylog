package lmm.moneylog.graphs.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lmm.moneylog.data.graphs.model.MonthlyTotal
import lmm.moneylog.graphs.R
import java.util.Locale
import kotlin.math.max
import lmm.moneylog.ui.theme.Size as ThemeSize

/**
 * A bar chart component displaying income and expenses per month
 *
 * @param data List of MonthlyTotal to display in the bar chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun BarChart(
    data: List<MonthlyTotal>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.graphs_no_data),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    val incomeColor = Color(0xFF4CAF50) // Green
    val expenseColor = Color(0xFFF44336) // Red

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ThemeSize.MediumSpaceSize)
    ) {
        // Legend
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(
                color = incomeColor,
                label = stringResource(R.string.graphs_bar_legend_income),
                modifier = Modifier.padding(end = ThemeSize.MediumSpaceSize)
            )
            LegendItem(
                color = expenseColor,
                label = stringResource(R.string.graphs_bar_legend_expenses)
            )
        }

        // Bar Chart
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = ThemeSize.DefaultSpaceSize)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barSpacing = 8.dp.toPx()
            val barGroupWidth = (canvasWidth - (data.size + 1) * barSpacing) / data.size
            val barWidth = (barGroupWidth - barSpacing) / 2

            // Find max value for scaling
            val maxValue = data.maxOfOrNull { max(it.income, it.expenses) } ?: 1.0
            val scale = if (maxValue > 0) (canvasHeight * 0.8f) / maxValue.toFloat() else 1f

            data.forEachIndexed { index, monthData ->
                val xStart = barSpacing + (index * (barGroupWidth + barSpacing))

                // Income bar (left)
                val incomeHeight = (monthData.income * scale).toFloat()
                if (incomeHeight > 0) {
                    drawRoundRect(
                        color = incomeColor,
                        topLeft = Offset(xStart, canvasHeight - incomeHeight),
                        size = Size(barWidth, incomeHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                }

                // Expense bar (right)
                val expenseHeight = (monthData.expenses * scale).toFloat()
                if (expenseHeight > 0) {
                    drawRoundRect(
                        color = expenseColor,
                        topLeft =
                            Offset(
                                xStart + barWidth + barSpacing / 2,
                                canvasHeight - expenseHeight
                            ),
                        size = Size(barWidth, expenseHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                }
            }
        }

        // Month labels
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { monthData ->
                Text(
                    text = monthData.monthName,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Summary statistics
        val totalIncome = data.sumOf { it.income }
        val totalExpenses = data.sumOf { it.expenses }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ThemeSize.DefaultSpaceSize),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.graphs_bar_total_income),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = String.format(Locale.getDefault(), "R$ %.2f", totalIncome),
                    style = MaterialTheme.typography.titleMedium,
                    color = incomeColor
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.graphs_bar_total_expenses),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = String.format(Locale.getDefault(), "R$ %.2f", totalExpenses),
                    style = MaterialTheme.typography.titleMedium,
                    color = expenseColor
                )
            }
        }
    }
}

/**
 * Legend item showing color indicator and label
 */
@Composable
private fun LegendItem(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ThemeSize.XSmallSpaceSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(color = color)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
