package lmm.moneylog.ui.features.gettransactions

import lmm.moneylog.domain.addtransaction.time.DomainTime

data class TransactionModel(
    val value: Double,
    val isIncome: Boolean,
    val description: String,
    val date: DomainTime
)
