package lmm.moneylog.data.graphs.model

/**
 * Model representing net worth at a specific point in time
 *
 * @property month The month number (1-12)
 * @property year The year
 * @property monthName The name of the month
 * @property netWorth The total net worth (cumulative balance) at this point
 */
data class NetWorthPoint(
    val month: Int,
    val year: Int,
    val monthName: String,
    val netWorth: Double
)
