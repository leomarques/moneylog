package lmm.moneylog.domain.addtransaction.time

interface DomainTimeConverter {
    fun toDomainTime(time: String): DomainTime
    fun getNowTime(): String
    fun getDatePattern(): String
}
