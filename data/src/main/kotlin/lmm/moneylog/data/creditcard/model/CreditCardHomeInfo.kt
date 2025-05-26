package lmm.moneylog.data.creditcard.model

data class CreditCardHomeInfoResult(
    val cards: List<CreditCardHomeInfo>,
    val invoiceCode: String
)

data class CreditCardHomeInfo(
    val id: Int,
    val name: String,
    val value: Double,
    val color: Long
)
