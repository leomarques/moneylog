package lmm.moneylog.ui.features.addtransaction.domaintime

import lmm.moneylog.data.domaintime.LocalDateTimeToDomainTimeConverterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalDateTimeToDomainTimeConverterImplTest {

    @Test
    fun `should convert date string in converter pattern to DomainTime`() {
        val converter = LocalDateTimeToDomainTimeConverterImpl()
        val domainTime = converter.timeStampToDomainTime(1683733471712)

        with(domainTime) {
            assertEquals(2023, year)
            assertEquals(5, month)
            assertEquals(10, day)
            assertEquals(12, hour)
            assertEquals(44, minute)
        }
    }
}
