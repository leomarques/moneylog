package lmm.moneylog.data.balance.model

data class TransactionBalance(
    val value: Double,
    val month: Int,
    val year: Int,
    val accountId: Int?
)
