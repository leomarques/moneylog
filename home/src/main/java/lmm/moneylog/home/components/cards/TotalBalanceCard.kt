package lmm.moneylog.home.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.ui.components.misc.CircularIconBox
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.home.models.BalanceInfo
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.IncomeColor
import lmm.moneylog.ui.theme.Size

/**
 * Callbacks for TotalBalanceCard interactions
 *
 * @property onCardClick Callback invoked when the balance card is clicked
 * @property onChangeIndicatorClick Callback invoked when the change indicator is clicked
 */
data class TotalBalanceCardCallbacks(
    val onCardClick: () -> Unit = {},
    val onChangeIndicatorClick: () -> Unit = {}
)

/**
 * Displays the total balance with change percentage and amount in a visually prominent card
 * with a gradient background
 *
 * @param balanceInfo The balance information to display
 * @param modifier Modifier for the card container
 * @param callbacks Callbacks for user interactions with the card
 * @param valuesMasked Whether to mask the monetary values
 */
@Composable
fun TotalBalanceCard(
    balanceInfo: BalanceInfo,
    modifier: Modifier = Modifier,
    callbacks: TotalBalanceCardCallbacks = TotalBalanceCardCallbacks(),
    valuesMasked: Boolean = false
) {
    Card(
        modifier = modifier.clickable { callbacks.onCardClick() },
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = TotalBalanceCardDefaults.CardElevation
            )
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.secondaryContainer
                                    )
                            )
                    ).padding(Size.MediumSpaceSize)
        ) {
            Column {
                BalanceCardHeader(
                    changePercentage = balanceInfo.changePercentage,
                    changeAmount = balanceInfo.changeAmount,
                    onChangeIndicatorClick = callbacks.onChangeIndicatorClick,
                    valuesMasked = valuesMasked
                )

                Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

                BalanceCardContent(
                    balance = balanceInfo.balance,
                    valuesMasked = valuesMasked
                )
            }
        }
    }
}

object TotalBalanceCardDefaults {
    val CardElevation = 4.dp
    val IconSize = 36.dp
    val IconInnerSize = 20.dp
    const val ICON_ALPHA = 0.9f
    const val LABEL_ALPHA = 0.7f
    const val CHANGE_AMOUNT_ALPHA = 0.8f
    val LetterSpacing = (-0.5).sp
}

@Composable
private fun BalanceCardHeader(
    changePercentage: String,
    changeAmount: String,
    onChangeIndicatorClick: () -> Unit,
    valuesMasked: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WalletIcon()

        Spacer(modifier = Modifier.weight(1f))

        ChangeIndicator(
            changePercentage = changePercentage,
            changeAmount = changeAmount,
            onClick = onChangeIndicatorClick,
            valuesMasked = valuesMasked
        )
    }
}

@Composable
private fun WalletIcon() {
    CircularIconBox(
        icon = Icons.Default.AccountBalanceWallet,
        contentDescription = "Wallet",
        backgroundColor =
            MaterialTheme.colorScheme.primary.copy(
                alpha = TotalBalanceCardDefaults.ICON_ALPHA
            ),
        iconTint = MaterialTheme.colorScheme.onPrimary,
        boxSize = TotalBalanceCardDefaults.IconSize,
        iconSize = TotalBalanceCardDefaults.IconInnerSize
    )
}

@Composable
private fun ChangeIndicator(
    changePercentage: String,
    changeAmount: String,
    onClick: () -> Unit,
    valuesMasked: Boolean
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = if (valuesMasked) "••••" else changePercentage,
            style = MaterialTheme.typography.labelLarge,
            color = IncomeColor,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = if (valuesMasked) "••••" else changeAmount,
            style = MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = TotalBalanceCardDefaults.CHANGE_AMOUNT_ALPHA
                )
        )
    }
}

@Composable
private fun BalanceCardContent(
    balance: String,
    valuesMasked: Boolean
) {
    Column {
        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.bodySmall,
            color =
                MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = TotalBalanceCardDefaults.LABEL_ALPHA
                ),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(Size.XXSmallSpaceSize))
        Text(
            text = if (valuesMasked) "••••••••" else balance,
            style =
                MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = TotalBalanceCardDefaults.LetterSpacing
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TotalBalanceCardPreview() {
    AppTheme {
        TotalBalanceCard(
            balanceInfo = HomePreviewData.sampleBalanceInfo(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize),
            valuesMasked = false
        )
    }
}
