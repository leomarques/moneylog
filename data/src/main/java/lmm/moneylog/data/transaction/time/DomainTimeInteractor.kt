package lmm.moneylog.data.transaction.time

interface DomainTimeInteractor {
    fun getCurrentTimeStamp(): Long
    fun getCurrentDomainTime(): DomainTime
    fun timeStampToDomainTime(timeStamp: Long): DomainTime
    fun getMonthName(number: Int): String
    fun getCurrentMonthName(): String
}
