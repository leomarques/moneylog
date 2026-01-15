package lmm.moneylog.home.ui.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.home.models.CategoryExpense
import lmm.moneylog.home.models.ExpensesSummary
import lmm.moneylog.ui.components.misc.CircularIconBox
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.neutralColor
import lmm.moneylog.ui.theme.outcome
import java.util.Locale
import androidx.compose.ui.geometry.Size as CanvasSize

/**
 * Card displaying current month expenses by category with pie chart
 *
 * @param summary The expenses summary data
 * @param modifier Modifier for the card container
 * @param onClick Callback when card is clicked
 * @param valuesMasked Whether to mask displayed values
 */
@Composable
fun RecentExpensesCard(
    summary: ExpensesSummary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    valuesMasked: Boolean = false
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize)
        ) {
            // Header with icon and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Size.SmallSpaceSize)
                ) {
                    CircularIconBox(
                        icon = Icons.Default.ArrowUpward,
                        contentDescription = stringResource(lmm.moneylog.home.R.string.home_expenses_card_cd),
                        backgroundColor = outcome.copy(alpha = 0.12f),
                        iconTint = outcome,
                        boxSize = 32.dp,
                        iconSize = 18.dp
                    )

                    Text(
                        text = stringResource(lmm.moneylog.home.R.string.home_expenses_card_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = if (valuesMasked) "••••••" else summary.totalExpenses,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = outcome
                )
            }

            if (summary.categoryExpenses.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

                // Pie chart and legend side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pie chart on the left
                    CategoryPieChart(
                        categories = summary.categoryExpenses,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(150.dp)
                    )

                    // Category legend on the right
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Size.XSmallSpaceSize)
                    ) {
                        summary.categoryExpenses.forEach { category ->
                            CategoryLegendItem(
                                category = category,
                                valuesMasked = valuesMasked
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(Size.SmallSpaceSize))
                Text(
                    text = stringResource(lmm.moneylog.home.R.string.home_expenses_card_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = Size.SmallSpaceSize)
                )
            }
        }
    }
}

/**
 * Pie chart showing category expenses
 */
@Composable
private fun CategoryPieChart(
    categories: List<CategoryExpense>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (categories.isEmpty()) return@Canvas

        val canvasSize = size.minDimension
        val radius = canvasSize / 2.5f
        val center = Offset(size.width / 2, size.height / 2)
        val arcSize = CanvasSize(radius * 2, radius * 2)
        val arcOffset = Offset(center.x - radius, center.y - radius)

        val segmentList = mutableListOf<Triple<Color, Float, Float>>() // color, startAngle, sweepAngle
        var currentAngle = -90f
        val totalPercentage = categories.sumOf { it.percentage.toDouble() }.toFloat()

        categories.forEach { category ->
            val sweepAngle = (category.percentage / 100f) * 360f
            segmentList.add(
                Triple(category.categoryColor ?: neutralColor, currentAngle, sweepAngle)
            )
            currentAngle += sweepAngle
        }

        if (totalPercentage < 100f) {
            val remainingSweepAngle = ((100f - totalPercentage) / 100f) * 360f
            segmentList.add(
                Triple(neutralColor.copy(alpha = 0.2f), currentAngle, remainingSweepAngle)
            )
        }

        segmentList.forEach { (color, angle, sweep) ->
            drawArc(
                color = color,
                startAngle = angle,
                sweepAngle = sweep,
                useCenter = true,
                topLeft = arcOffset,
                size = arcSize
            )
        }
    }
}

/**
 * Legend item for a category
 */
@Composable
private fun CategoryLegendItem(
    category: CategoryExpense,
    valuesMasked: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Size.XSmallSpaceSize)
        ) {
            // Category color indicator
            Canvas(modifier = Modifier.size(10.dp)) {
                drawCircle(color = category.categoryColor ?: neutralColor)
            }

            Text(
                text = category.categoryName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text =
                if (valuesMasked) {
                    "••••"
                } else {
                    "${category.amount.formatForRs()} (${
                        String.format(
                            Locale.getDefault(),
                            "%.0f%%",
                            category.percentage
                        )
                    })"
                },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 14.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentExpensesCardPreview() {
    AppTheme {
        RecentExpensesCard(
            summary =
                ExpensesSummary(
                    totalExpenses = "R$ 2.450,00",
                    totalAmount = 2450.0,
                    categoryExpenses =
                        listOf(
                            CategoryExpense(
                                categoryName = "Alimentação",
                                categoryColor = Color(0xFFFF9800),
                                amount = 850.0,
                                percentage = 34.7f
                            ),
                            CategoryExpense(
                                categoryName = "Transporte",
                                categoryColor = Color(0xFF4CAF50),
                                amount = 600.0,
                                percentage = 24.5f
                            ),
                            CategoryExpense(
                                categoryName = "Contas",
                                categoryColor = Color(0xFF2196F3),
                                amount = 500.0,
                                percentage = 20.4f
                            ),
                            CategoryExpense(
                                categoryName = "Lazer",
                                categoryColor = Color(0xFF9C27B0),
                                amount = 300.0,
                                percentage = 12.2f
                            ),
                            CategoryExpense(
                                categoryName = "Saúde",
                                categoryColor = Color(0xFFF44336),
                                amount = 200.0,
                                percentage = 8.2f
                            )
                        )
                ),
            modifier = Modifier.padding(Size.DefaultSpaceSize)
        )
    }
}
