package lmm.moneylog.data.time.repositories

import lmm.moneylog.data.time.model.DomainTime
import java.time.Instant
import java.time.Month
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

class DomainTimeRepositoryImpl : DomainTimeRepository {
    override fun getCurrentTimeStamp() = Instant.now().toEpochMilli()

    override fun getCurrentDomainTime() = timeStampToDomainTime(getCurrentTimeStamp())

    override fun timeStampToDomainTime(timeStamp: Long): DomainTime {
        val localDate =
            Instant
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
        Month.of(number).getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        )

    override fun getCurrentMonthName() =
        getCurrentDomainTime().month
            .let(::getMonthName)
            .replaceFirstChar(Char::titlecase)

    override fun getCurrentInvoice(): String {
        val time = getCurrentDomainTime()
        return "${time.month}.${time.year}"
    }
}
