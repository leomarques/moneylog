package lmm.moneylog.home.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.ui.components.misc.CircularIconBox
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.IncomeColor
import lmm.moneylog.ui.theme.Size

/**
 * Displays income or expense summary in a compact card with an icon and amount
 *
 * @param title The title/label for this card (e.g., "Income", "Expenses")
 * @param amount The monetary amount to display
 * @param icon The icon to display representing the type of transaction
 * @param iconColor The color for the icon and its background
 * @param modifier Modifier for the card container
 * @param onClick Callback invoked when the card is clicked
 * @param valuesMasked Whether to mask the monetary value
 */
@Composable
fun IncomeExpenseCard(
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    valuesMasked: Boolean = false
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = IncomeExpenseCardDefaults.CARD_ELEVATION
            )
    ) {
        Column(
            modifier = Modifier.padding(Size.DefaultSpaceSize)
        ) {
            CardIcon(
                icon = icon,
                iconColor = iconColor,
                contentDescription = title
            )

            Spacer(modifier = Modifier.height(Size.SmallSpaceSize))

            CardLabel(title = title)

            Spacer(modifier = Modifier.height(Size.XXSmallSpaceSize))

            CardAmount(amount = amount, valuesMasked = valuesMasked)
        }
    }
}

object IncomeExpenseCardDefaults {
    val CARD_ELEVATION = 2.dp
    val ICON_SIZE = 32.dp
    val ICON_INNER_SIZE = 18.dp
    const val ICON_BACKGROUND_ALPHA = 0.12f
    const val LABEL_ALPHA = 0.7f
    val LETTER_SPACING = (-0.3).sp
}

@Composable
private fun CardIcon(
    icon: ImageVector,
    iconColor: Color,
    contentDescription: String
) {
    CircularIconBox(
        icon = icon,
        contentDescription = contentDescription,
        backgroundColor = iconColor.copy(alpha = IncomeExpenseCardDefaults.ICON_BACKGROUND_ALPHA),
        iconTint = iconColor,
        boxSize = IncomeExpenseCardDefaults.ICON_SIZE,
        iconSize = IncomeExpenseCardDefaults.ICON_INNER_SIZE
    )
}

@Composable
private fun CardLabel(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color =
            MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = IncomeExpenseCardDefaults.LABEL_ALPHA
            ),
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun CardAmount(
    amount: String,
    valuesMasked: Boolean
) {
    Text(
        text = if (valuesMasked) "••••••" else amount,
        style =
            MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = IncomeExpenseCardDefaults.LETTER_SPACING
            ),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Preview(showBackground = true)
@Composable
private fun IncomeExpenseCardPreview() {
    AppTheme {
        IncomeExpenseCard(
            title = "Income",
            amount = HomePreviewData.sampleIncome().amount,
            icon = Icons.Default.ArrowDownward,
            iconColor = IncomeColor,
            modifier = Modifier.padding(Size.DefaultSpaceSize),
            valuesMasked = false
        )
    }
}
