package lmm.moneylog.data.graphs.model

/**
 * Model representing total income and expenses for a specific month
 *
 * @property month The month number (1-12)
 * @property year The year
 * @property monthName The name of the month
 * @property income Total income for the month
 * @property expenses Total expenses for the month (as positive value)
 */
data class MonthlyTotal(
    val month: Int,
    val year: Int,
    val monthName: String,
    val income: Double,
    val expenses: Double
)
