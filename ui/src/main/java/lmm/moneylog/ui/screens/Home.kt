package lmm.moneylog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CreditCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lmm.moneylog.ui.components.HomeHeader
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

@Composable
fun Home(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(Size.DefaultSpaceSize)
    ) {
        HomeHeader(
            text = "October 2023",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.DefaultSpaceSize)
        )

        // Total Balance Card - Main Feature
        TotalBalanceCard(
            balance = "$12,345.67",
            changePercentage = "+8.2%",
            changeAmount = "+$932.45",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.DefaultSpaceSize)
        )

        // Income & Expenses Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize)
        ) {
            IncomeExpenseCard(
                title = "Income",
                amount = "$8,420.00",
                icon = Icons.Default.ArrowDownward,
                iconColor = income,
                modifier = Modifier.weight(1f)
            )
            IncomeExpenseCard(
                title = "Expenses",
                amount = "$5,687.55",
                icon = Icons.Default.ArrowUpward,
                iconColor = outcome,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

        // Credit Cards Section
        CreditCardsCard(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TotalBalanceCard(
    balance: String,
    changePercentage: String,
    changeAmount: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    )
                    .padding(Size.MediumSpaceSize)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Wallet",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = changePercentage,
                            style = MaterialTheme.typography.titleMedium,
                            color = income,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = changeAmount,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = balance,
                    style =
                        MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun IncomeExpenseCard(
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(Size.DefaultSpaceSize)
        ) {
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(Size.SmallSpaceSize))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = amount,
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun CreditCardsCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Size.DefaultSpaceSize)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = "Credit Cards",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Credit Cards",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = Size.SmallSpaceSize)
                )
            }

            Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

            // Credit Card Items
            CreditCardItem(
                cardName = "Visa Platinum",
                cardLastDigits = "•••• 4532",
                invoiceAmount = "$1,234.56",
                cardColor = Color(0xFF1A1F71)
            )

            Spacer(modifier = Modifier.height(Size.SmallSpaceSize))

            CreditCardItem(
                cardName = "Mastercard Gold",
                cardLastDigits = "•••• 8901",
                invoiceAmount = "$892.30",
                cardColor = Color(0xFFEB8C00)
            )

            Spacer(modifier = Modifier.height(Size.SmallSpaceSize))

            CreditCardItem(
                cardName = "American Express",
                cardLastDigits = "•••• 1234",
                invoiceAmount = "$2,156.78",
                cardColor = Color(0xFF006FCF)
            )
        }
    }
}

@Composable
private fun CreditCardItem(
    cardName: String,
    cardLastDigits: String,
    invoiceAmount: String,
    cardColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = Size.XXSmallSpaceSize),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Card color indicator
            Box(
                modifier =
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(cardColor)
            )

            Column(modifier = Modifier.padding(start = Size.SmallSpaceSize)) {
                Text(
                    text = cardName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = cardLastDigits,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = invoiceAmount,
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    AppTheme {
        Home()
    }
}
