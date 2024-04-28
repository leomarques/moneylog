package lmm.moneylog.data.creditcard.model

data class CreditCard(
    val id: Int = -1,
    val name: String,
    val closingDay: Int,
    val dueDay: Int,
    val limit: Int,
    val color: Long
)
