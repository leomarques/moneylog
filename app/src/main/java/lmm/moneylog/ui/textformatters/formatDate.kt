package lmm.moneylog.ui.textformatters

import lmm.moneylog.domain.time.DomainTime

fun DomainTime.formatDate() = "$day/$month/$year"
