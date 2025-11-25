package lmm.moneylog.data.transaction.nubank.model

data class NubankTransactionInfo(
    val value: String,
    val place: String,
    val currency: String = "R$",
    val categoryId: Int? = null,
    val creditCardId: Int? = null,
    val creditCardClosingDay: Int? = null
)
