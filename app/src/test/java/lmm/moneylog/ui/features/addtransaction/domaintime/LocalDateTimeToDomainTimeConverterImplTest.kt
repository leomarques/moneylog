package lmm.moneylog.ui.features.addtransaction.domaintime

import lmm.moneylog.data.domaintime.LocalDateToDomainTimeConverterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalDateTimeToDomainTimeConverterImplTest {

    @Test
    fun `should convert timestamp to DomainTime`() {
        val converter = LocalDateToDomainTimeConverterImpl()
        val domainTime = converter.timeStampToDomainTime(1683733471712)

        with(domainTime) {
            assertEquals(2023, year)
            assertEquals(5, month)
            assertEquals(10, day)
        }
    }
}
