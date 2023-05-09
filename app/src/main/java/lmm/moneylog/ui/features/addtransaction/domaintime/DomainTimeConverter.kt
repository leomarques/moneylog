package lmm.moneylog.ui.features.addtransaction.domaintime

import lmm.moneylog.domain.addtransaction.model.DomainTime

interface DomainTimeConverter {
    fun toDomainTime(time: String): DomainTime
    fun getNowTime(): String
    fun getDatePattern(): String
}
