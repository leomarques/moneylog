package lmm.moneylog.data.domaintime

import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter
import java.time.Instant
import java.time.Month
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

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

    override fun getMonthName(number: Int): String =
        Month.of(number).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
}
