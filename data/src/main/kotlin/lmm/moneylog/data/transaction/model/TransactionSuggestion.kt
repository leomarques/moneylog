package lmm.moneylog.data.transaction.model

data class TransactionSuggestion(
    val description: String,
    val value: Double,
    val categoryId: Int?,
    val categoryColor: Long,
    val accountId: Int?,
    val creditCardId: Int?,
    val invoiceMonth: Int?,
    val invoiceYear: Int?
)
