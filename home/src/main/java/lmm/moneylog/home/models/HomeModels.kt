package lmm.moneylog.home.models

import androidx.compose.ui.graphics.Color

/**
 * Data model representing balance information with change metrics
 *
 * @property balance The current balance formatted as a string (e.g., "$12,345.67")
 * @property changePercentage The percentage change in balance (e.g., "+8.2%")
 * @property changeAmount The absolute change in balance (e.g., "+$932.45")
 */
data class BalanceInfo(
    val balance: String,
    val changePercentage: String,
    val changeAmount: String
)

/**
 * Data model for income or expense summary
 *
 * @property amount The monetary amount formatted as a string (e.g., "$8,420.00")
 */
data class FinancialSummary(
    val amount: String
)

/**
 * Data model representing a credit card with invoice information
 *
 * @property cardId The unique identifier for the credit card
 * @property cardName The display name of the credit card (e.g., "Visa Platinum")
 * @property cardLastDigits The last 4 digits of the card number (e.g., "•••• 4532")
 * @property invoiceAmount The current invoice amount formatted as a string (e.g., "$1,234.56")
 * @property invoiceCode The invoice code identifier for the current invoice period
 * @property cardColor The brand/theme color associated with this card
 */
data class CreditCardInfo(
    val cardId: Int,
    val cardName: String,
    val cardLastDigits: String,
    val invoiceAmount: String,
    val invoiceCode: String,
    val cardColor: Color
)
