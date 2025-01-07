package lmm.moneylog.data.time.repositories

import lmm.moneylog.data.time.model.DomainTime

interface DomainTimeRepository {
    fun getCurrentTimeStamp(): Long

    fun getCurrentDomainTime(): DomainTime

    fun timeStampToDomainTime(timeStamp: Long): DomainTime

    fun getMonthName(number: Int): String

    fun getCurrentMonthName(): String

    fun getCurrentInvoice(): String

    fun getMonthNameFromInvoiceCode(invoiceCode: String): String
}
