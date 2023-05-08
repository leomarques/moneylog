package lmm.moneylog.domain.addtransaction.model

data class Transaction(
    val value: Double,
    val description: String,
    val date: MoneylogTime
)
