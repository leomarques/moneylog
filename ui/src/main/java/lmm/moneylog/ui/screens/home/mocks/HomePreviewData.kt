package lmm.moneylog.ui.screens.home.mocks

import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.screens.home.models.BalanceInfo
import lmm.moneylog.ui.screens.home.models.CreditCardInfo
import lmm.moneylog.ui.screens.home.models.FinancialSummary
import lmm.moneylog.ui.screens.home.models.HomeScreenData

/**
 * Provides preview/sample data for the home screen components
 */
object HomePreviewData {
    const val SAMPLE_PERIOD_TITLE = "October 2023"

    private const val VISA_PLATINUM_COLOR = 0xFF1A1F71
    private const val MASTERCARD_GOLD_COLOR = 0xFFEB8C00
    private const val AMEX_BLUE_COLOR = 0xFF006FCF

    fun sampleHomeScreenData() =
        HomeScreenData(
            periodTitle = SAMPLE_PERIOD_TITLE,
            balanceInfo = sampleBalanceInfo(),
            income = sampleIncome(),
            expenses = sampleExpenses(),
            creditCards = sampleCreditCards()
        )

    fun sampleBalanceInfo() =
        BalanceInfo(
            balance = "$12,345.67",
            changePercentage = "+8.2%",
            changeAmount = "+$932.45"
        )

    fun sampleIncome() =
        FinancialSummary(
            title = "Income",
            amount = "$8,420.00"
        )

    fun sampleExpenses() =
        FinancialSummary(
            title = "Expenses",
            amount = "$5,687.55"
        )

    fun sampleCreditCards() =
        listOf(
            CreditCardInfo(
                cardName = "Visa Platinum",
                cardLastDigits = "•••• 4532",
                invoiceAmount = "$1,234.56",
                cardColor = Color(VISA_PLATINUM_COLOR)
            ),
            CreditCardInfo(
                cardName = "Mastercard Gold",
                cardLastDigits = "•••• 8901",
                invoiceAmount = "$892.30",
                cardColor = Color(MASTERCARD_GOLD_COLOR)
            ),
            CreditCardInfo(
                cardName = "American Express",
                cardLastDigits = "•••• 1234",
                invoiceAmount = "$2,156.78",
                cardColor = Color(AMEX_BLUE_COLOR)
            )
        )
}
