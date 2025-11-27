package lmm.moneylog.ui.screens.home.models

import androidx.compose.ui.graphics.Color

/**
 * Data model representing balance information with change metrics
 */
data class BalanceInfo(
    val balance: String,
    val changePercentage: String,
    val changeAmount: String
)

/**
 * Data model for income or expense summary
 */
data class FinancialSummary(
    val title: String,
    val amount: String
)

/**
 * Data model representing a credit card with invoice information
 */
data class CreditCardInfo(
    val cardName: String,
    val cardLastDigits: String,
    val invoiceAmount: String,
    val cardColor: Color
)

/**
 * Data model containing all home screen data
 */
data class HomeScreenData(
    val periodTitle: String,
    val balanceInfo: BalanceInfo,
    val income: FinancialSummary,
    val expenses: FinancialSummary,
    val creditCards: List<CreditCardInfo>
)
