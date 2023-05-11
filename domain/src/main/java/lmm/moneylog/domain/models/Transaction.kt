package lmm.moneylog.domain.models

import lmm.moneylog.domain.time.DomainTime

data class Transaction(
    val value: Double,
    val description: String,
    val date: DomainTime
)
