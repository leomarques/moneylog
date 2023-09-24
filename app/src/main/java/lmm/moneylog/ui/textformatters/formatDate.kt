package lmm.moneylog.ui.textformatters

import lmm.moneylog.data.transaction.time.DomainTime

fun DomainTime.formatDate() = "$day/$month/$year"
