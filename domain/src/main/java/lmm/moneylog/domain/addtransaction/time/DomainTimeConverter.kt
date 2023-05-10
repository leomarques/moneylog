package lmm.moneylog.domain.addtransaction.time

interface DomainTimeConverter {
    fun getCurrentTimeStamp(): Long
    fun timeStampToDomainTime(timeStamp: Long): DomainTime
    fun getMonthName(number: Int): String
}
