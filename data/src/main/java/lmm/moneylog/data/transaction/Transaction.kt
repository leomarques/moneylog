package lmm.moneylog.data.transaction

import lmm.moneylog.data.transaction.time.DomainTime

data class Transaction(
    val id: Int = -1,
    val value: Double,
    val description: String,
    val date: DomainTime,
    val accountId: Int? = null,
    val categoryId: Int? = null
)
