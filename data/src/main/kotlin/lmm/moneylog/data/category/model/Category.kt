package lmm.moneylog.data.category.model

data class Category(
    val id: Int = -1,
    val name: String,
    val color: Long,
    val isIncome: Boolean
)
