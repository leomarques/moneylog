package lmm.moneylog.home.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.ui.components.misc.CircularIconBox
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.home.models.CreditCardInfo
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Callbacks for CreditCardsCard interactions
 *
 * @property onCardClick Callback invoked when a credit card item is clicked, providing the card info
 */
data class CreditCardsCardCallbacks(
    val onCardClick: (CreditCardInfo) -> Unit = {}
)

/**
 * Displays a list of credit cards with their invoice amounts in a single card container
 *
 * @param creditCards List of credit card information to display
 * @param modifier Modifier for the card container
 * @param callbacks Callbacks for user interactions with credit card items
 * @param valuesMasked Whether to mask the monetary values
 */
@Composable
fun CreditCardsCard(
    creditCards: List<CreditCardInfo>,
    modifier: Modifier = Modifier,
    callbacks: CreditCardsCardCallbacks = CreditCardsCardCallbacks(),
    valuesMasked: Boolean = false
) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = CreditCardsCardDefaults.CardElevation
            )
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize)
        ) {
            CreditCardsHeader()

            Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

            creditCards.forEachIndexed { index, card ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(Size.SmallSpaceSize))
                }
                CreditCardItem(
                    cardInfo = card,
                    onClick = { callbacks.onCardClick(card) },
                    valuesMasked = valuesMasked
                )
            }
        }
    }
}

object CreditCardsCardDefaults {
    val CardElevation = 3.dp
    val IconSize = 32.dp
    val IconInnerSize = 18.dp
    const val ICON_ALPHA = 0.9f
    val ColorIndicatorSize = 10.dp
    const val LABEL_ALPHA = 0.7f
    val AmountLetterSpacing = (-0.2).sp
}

@Composable
private fun CreditCardsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIconBox(
            icon = Icons.Default.CreditCard,
            contentDescription = "Credit Cards",
            backgroundColor =
                MaterialTheme.colorScheme.tertiary.copy(
                    alpha = CreditCardsCardDefaults.ICON_ALPHA
                ),
            iconTint = MaterialTheme.colorScheme.onTertiary,
            boxSize = CreditCardsCardDefaults.IconSize,
            iconSize = CreditCardsCardDefaults.IconInnerSize
        )
        Text(
            text = "Credit Cards",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = Size.SmallSpaceSize)
        )
    }
}

@Composable
private fun CreditCardItem(
    cardInfo: CreditCardInfo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    valuesMasked: Boolean = false
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = Size.SmallSpaceSize),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CreditCardDetails(
            cardName = cardInfo.cardName,
            cardLastDigits = cardInfo.cardLastDigits,
            cardColor = cardInfo.cardColor
        )

        CreditCardAmount(
            amount = cardInfo.invoiceAmount,
            valuesMasked = valuesMasked
        )
    }
}

@Composable
private fun CreditCardDetails(
    cardName: String,
    cardLastDigits: String,
    cardColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier =
                Modifier
                    .size(CreditCardsCardDefaults.ColorIndicatorSize)
                    .clip(CircleShape)
                    .background(cardColor)
        )

        Column(modifier = Modifier.padding(start = Size.SmallSpaceSize)) {
            Text(
                text = cardName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = cardLastDigits,
                style = MaterialTheme.typography.labelSmall,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = CreditCardsCardDefaults.LABEL_ALPHA
                    )
            )
        }
    }
}

@Composable
private fun CreditCardAmount(
    amount: String,
    valuesMasked: Boolean
) {
    Text(
        text = if (valuesMasked) "••••••" else amount,
        style =
            MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = CreditCardsCardDefaults.AmountLetterSpacing
            ),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Preview(showBackground = true)
@Composable
private fun CreditCardsCardPreview() {
    AppTheme {
        CreditCardsCard(
            creditCards = HomePreviewData.sampleCreditCards(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize),
            valuesMasked = false
        )
    }
}
