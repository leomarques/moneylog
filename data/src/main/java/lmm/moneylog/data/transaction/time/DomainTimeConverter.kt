package lmm.moneylog.data.transaction.time

interface DomainTimeConverter {
    fun getCurrentTimeStamp(): Long
    fun getCurrentDomainTime(): DomainTime
    fun timeStampToDomainTime(timeStamp: Long): DomainTime
    fun getMonthName(number: Int): String
}
