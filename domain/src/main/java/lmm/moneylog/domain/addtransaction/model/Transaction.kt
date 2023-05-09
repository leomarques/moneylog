package lmm.moneylog.domain.addtransaction.model

import lmm.moneylog.domain.addtransaction.time.DomainTime

data class Transaction(
    val value: Double,
    val description: String,
    val date: DomainTime
)
