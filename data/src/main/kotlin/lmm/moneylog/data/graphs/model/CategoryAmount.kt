package lmm.moneylog.data.graphs.model

/**
 * Model representing the total amount for a specific category
 *
 * @property categoryId The ID of the category (null for uncategorized transactions)
 * @property categoryName The name of the category
 * @property categoryColor The color of the category (null for uncategorized transactions)
 * @property totalAmount The total amount (absolute value) for this category
 * @property percentage The percentage this category represents of the total
 */
data class CategoryAmount(
    val categoryId: Int?,
    val categoryName: String,
    val categoryColor: Long?,
    val totalAmount: Double,
    val percentage: Float
)
