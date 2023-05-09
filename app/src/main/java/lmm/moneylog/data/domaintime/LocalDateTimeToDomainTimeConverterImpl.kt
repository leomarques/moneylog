package lmm.moneylog.data.domaintime

import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.addtransaction.time.DomainTimeConverter
import lmm.moneylog.domain.addtransaction.time.DomainTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeToDomainTimeConverterImpl : DomainTimeConverter {

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(getDatePattern())

    override fun toDomainTime(time: String): DomainTime {
        try {
            val localDateTime = LocalDateTime.parse(time, dateTimeFormatter)

            with(localDateTime) {
                return DomainTime(
                    day = dayOfMonth,
                    month = month.value,
                    year = year,
                    hour = hour,
                    minute = minute
                )
            }
        } catch (exception: DateTimeParseException) {
            throw exception.toDomainTimeException()
        }
    }

    override fun getNowTime(): String {
        return LocalDateTime.now().format(dateTimeFormatter)
    }

    override fun getDatePattern() = "yyyy-MM-dd HH:mm"
}

private fun DateTimeParseException.toDomainTimeException(): Throwable {
    throw DomainTimeException("message = $message")
}
