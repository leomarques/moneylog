package lmm.moneylog.data.domaintime

import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.addtransaction.time.DomainTimeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId

class LocalDateTimeToDomainTimeConverterImpl : DomainTimeConverter {

    override fun getCurrentTimeStamp() =
        Instant.now().toEpochMilli()

    override fun timeStampToDomainTime(timeStamp: Long): DomainTime {
        val localDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeStamp),
            ZoneId.systemDefault()
        )

        with(localDate) {
            return DomainTime(
                day = dayOfMonth,
                month = month.value,
                year = year,
                hour = hour,
                minute = minute
            )
        }
    }
    override fun getMonthName(number: Int) = Month.of(number).name.lowercase()
}
