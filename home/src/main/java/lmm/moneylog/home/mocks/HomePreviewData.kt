package lmm.moneylog.home.mocks

import androidx.compose.ui.graphics.Color
import lmm.moneylog.home.models.BalanceInfo
import lmm.moneylog.home.models.CreditCardInfo
import lmm.moneylog.home.models.FinancialSummary
import lmm.moneylog.home.ui.HomeLayoutCallbacks
import lmm.moneylog.home.ui.cards.CreditCardsCardCallbacks
import lmm.moneylog.home.ui.cards.TotalBalanceCardCallbacks
import lmm.moneylog.home.viewmodels.PeriodInfo

/**
 * Provides preview/sample data for the home screen components
 */
object HomePreviewData {
    val SAMPLE_PERIOD_INFO =
        PeriodInfo(
            monthName = "October",
            year = 2023
        )

    private const val VISA_PLATINUM_COLOR = 0xFF1A1F71
    private const val MASTERCARD_GOLD_COLOR = 0xFFEB8C00
    private const val AMEX_BLUE_COLOR = 0xFF006FCF

    fun sampleBalanceInfo() =
        BalanceInfo(
            balance = "$12,345.67",
            changePercentage = "+8.2%",
            changeAmount = "+$932.45"
        )

    fun sampleBalanceCardCallbacks() =
        TotalBalanceCardCallbacks(
            onCardClick = { /* Mock: Card clicked */ },
            onChangeIndicatorClick = { /* Mock: Change indicator clicked */ }
        )

    fun sampleCreditCardsCallbacks() =
        CreditCardsCardCallbacks(
            onCardClick = { /* Mock: Credit card clicked */ }
        )

    fun sampleHomeLayoutCallbacks() =
        HomeLayoutCallbacks(
            balanceCardCallbacks = sampleBalanceCardCallbacks(),
            onIncomeClick = { /* Mock: Income clicked */ },
            onExpensesClick = { /* Mock: Expenses clicked */ },
            creditCardsCallbacks = sampleCreditCardsCallbacks(),
            onExpensesCardClick = { /* Mock: Expenses card clicked */ }
        )

    fun sampleIncome() =
        FinancialSummary(
            amount = "$8,420.00"
        )

    fun sampleCreditCards() =
        listOf(
            CreditCardInfo(
                cardId = 1,
                cardName = "Visa Platinum",
                cardLastDigits = "•••• 4532",
                invoiceAmount = "$1,234.56",
                invoiceCode = "202310",
                cardColor = Color(VISA_PLATINUM_COLOR)
            ),
            CreditCardInfo(
                cardId = 2,
                cardName = "Mastercard Gold",
                cardLastDigits = "•••• 8901",
                invoiceAmount = "$892.30",
                invoiceCode = "202310",
                cardColor = Color(MASTERCARD_GOLD_COLOR)
            ),
            CreditCardInfo(
                cardId = 3,
                cardName = "American Express",
                cardLastDigits = "•••• 1234",
                invoiceAmount = "$2,156.78",
                invoiceCode = "202310",
                cardColor = Color(AMEX_BLUE_COLOR)
            )
        )
}
