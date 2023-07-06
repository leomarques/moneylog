package lmm.moneylog.domain.transaction

import lmm.moneylog.domain.time.DomainTime

data class Transaction(
    val id: Int = -1,
    val value: Double,
    val description: String,
    val date: DomainTime
)
