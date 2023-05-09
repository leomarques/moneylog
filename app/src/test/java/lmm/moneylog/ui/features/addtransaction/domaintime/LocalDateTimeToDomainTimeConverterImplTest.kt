package lmm.moneylog.ui.features.addtransaction.domaintime

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeToDomainTimeConverterImplTest {

    @Test
    fun `should convert date string in converter pattern to DomainTime`() {
        val converter = LocalDateTimeToDomainTimeConverterImpl()

        val text = "2022-01-06 21:30"
        val dateTimeFormatter = DateTimeFormatter.ofPattern(converter.getDatePattern())

        val localDateString = LocalDateTime.parse(text, dateTimeFormatter).format(dateTimeFormatter)
        val domainTime = converter.toDomainTime(localDateString)

        assertEquals(2022, domainTime.year)
        assertEquals(1, domainTime.month)
        assertEquals(6, domainTime.day)
        assertEquals(21, domainTime.hour)
        assertEquals(30, domainTime.minute)
    }
}
