package lmm.moneylog.ui.screens.home.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.ui.screens.home.mocks.HomePreviewData
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income

/**
 * Displays income or expense summary card
 */
@Composable
fun IncomeExpenseCard(
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
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

            CardAmount(amount = amount)
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
    Box(
        modifier =
            Modifier
                .size(IncomeExpenseCardDefaults.ICON_SIZE)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = IncomeExpenseCardDefaults.ICON_BACKGROUND_ALPHA)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(IncomeExpenseCardDefaults.ICON_INNER_SIZE)
        )
    }
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
private fun CardAmount(amount: String) {
    Text(
        text = amount,
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
            title = HomePreviewData.sampleIncome().title,
            amount = HomePreviewData.sampleIncome().amount,
            icon = Icons.Default.ArrowDownward,
            iconColor = income,
            modifier = Modifier.padding(Size.DefaultSpaceSize)
        )
    }
}
