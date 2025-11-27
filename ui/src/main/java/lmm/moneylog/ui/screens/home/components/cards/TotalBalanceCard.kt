package lmm.moneylog.ui.screens.home.components.cards

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.ui.screens.home.mocks.HomePreviewData
import lmm.moneylog.ui.screens.home.models.BalanceInfo
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income

/**
 * Displays the total balance with change percentage and amount
 */
@Composable
fun TotalBalanceCard(
    balanceInfo: BalanceInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                    changeAmount = balanceInfo.changeAmount
                )

                Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

                BalanceCardContent(balance = balanceInfo.balance)
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
    changeAmount: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WalletIcon()

        Spacer(modifier = Modifier.weight(1f))

        ChangeIndicator(
            changePercentage = changePercentage,
            changeAmount = changeAmount
        )
    }
}

@Composable
private fun WalletIcon() {
    Box(
        modifier =
            Modifier
                .size(TotalBalanceCardDefaults.IconSize)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = TotalBalanceCardDefaults.ICON_ALPHA
                    )
                ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountBalanceWallet,
            contentDescription = "Wallet",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(TotalBalanceCardDefaults.IconInnerSize)
        )
    }
}

@Composable
private fun ChangeIndicator(
    changePercentage: String,
    changeAmount: String
) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = changePercentage,
            style = MaterialTheme.typography.labelLarge,
            color = income,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = changeAmount,
            style = MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = TotalBalanceCardDefaults.CHANGE_AMOUNT_ALPHA
                )
        )
    }
}

@Composable
private fun BalanceCardContent(balance: String) {
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
            text = balance,
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
                    .padding(Size.DefaultSpaceSize)
        )
    }
}
