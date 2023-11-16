package lmm.moneylog.data.transaction.model

import lmm.moneylog.data.time.model.DomainTime

data class Transaction(
    val id: Int = -1,
    val value: Double,
    val description: String,
    val date: DomainTime,
    val accountId: Int? = null,
    val categoryId: Int? = null
)
