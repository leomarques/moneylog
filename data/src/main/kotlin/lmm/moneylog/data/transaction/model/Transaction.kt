package lmm.moneylog.data.transaction.model

import lmm.moneylog.data.time.model.DomainTime

data class Transaction(
    val id: Long = -1L,
    val value: Double,
    val description: String,
    val date: DomainTime,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val creditCardId: Int? = null,
    val invoiceMonth: Int? = null,
    val invoiceYear: Int? = null
)
