package lmm.moneylog.ui.textformatters

import lmm.moneylog.domain.addtransaction.time.DomainTime

fun DomainTime.formatDate() = "$day/$month/$year"
