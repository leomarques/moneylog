package lmm.moneylog.data.domaintime

import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.addtransaction.time.DomainTimeConverter
import java.time.Instant
import java.time.Month
import java.time.ZoneId

class LocalDateToDomainTimeConverterImpl : DomainTimeConverter {

    override fun getCurrentTimeStamp() =
        Instant.now().toEpochMilli()

    override fun timeStampToDomainTime(timeStamp: Long): DomainTime {
        val localDate = Instant
            .ofEpochMilli(timeStamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        with(localDate) {
            return DomainTime(
                day = dayOfMonth,
                month = month.value,
                year = year
            )
        }
    }

    override fun getMonthName(number: Int) = Month.of(number).name.lowercase()
}
