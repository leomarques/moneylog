package lmm.moneylog.notification.model

data class NotificationTransactionInfo(
    val value: String,
    val place: String,
    val currency: String = "R$",
    val category: String? = null,
    val categoryId: Int? = null,
    val creditCardId: Int? = null,
    val creditCardClosingDay: Int? = null
)
