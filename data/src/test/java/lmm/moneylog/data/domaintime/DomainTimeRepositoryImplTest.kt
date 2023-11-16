package lmm.moneylog.data.domaintime

import lmm.moneylog.data.time.repositories.DomainTimeRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainTimeRepositoryImplTest {

    @Test
    fun `should convert timestamp to DomainTime`() {
        val converter = DomainTimeRepositoryImpl()
        val domainTime = converter.timeStampToDomainTime(1683733471712)

        with(domainTime) {
            assertEquals(2023, year)
            assertEquals(5, month)
            assertEquals(10, day)
        }
    }
}
