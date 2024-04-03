package lmm.moneylog.data.domaintime

import lmm.moneylog.data.time.repositories.DomainTimeRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.DateTimeException

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

    @Test
    fun `should return current timestamp in milliseconds`() {
        val repository = DomainTimeRepositoryImpl()
        val currentTimestamp = repository.getCurrentTimeStamp()

        assertTrue(currentTimestamp > 0)
    }

    @Test
    fun `should return valid current DomainTime`() {
        val repository = DomainTimeRepositoryImpl()
        val currentDomainTime = repository.getCurrentDomainTime()

        assertTrue(currentDomainTime.year > 0)
        assertTrue(currentDomainTime.month in 1..12)
        assertTrue(currentDomainTime.day in 1..31)
    }

    @Test(expected = DateTimeException::class)
    fun `should throw DateTimeException for invalid month number`() {
        val repository = DomainTimeRepositoryImpl()

        repository.getMonthName(13)
    }
}
