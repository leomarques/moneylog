package lmm.moneylog.data.creditcard.utils

import io.mockk.every
import io.mockk.mockk
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InvoiceCalculatorTest {
    private lateinit var domainTimeRepository: DomainTimeRepository
    private lateinit var invoiceCalculator: InvoiceCalculator

    @Before
    fun setup() {
        domainTimeRepository = mockk()
        invoiceCalculator = InvoiceCalculator(domainTimeRepository)
    }

    @Test
    fun `calculateInvoiceForCard should return current month when current day is before closing day`() {
        val currentTime = DomainTime(day = 5, month = 6, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceForCard(closingDay)

        assertEquals("6.2024", result)
    }

    @Test
    fun `calculateInvoiceForCard should return next month when current day is equal to closing day`() {
        val currentTime = DomainTime(day = 10, month = 6, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceForCard(closingDay)

        assertEquals("7.2024", result)
    }

    @Test
    fun `calculateInvoiceForCard should return next month when current day is after closing day`() {
        val currentTime = DomainTime(day = 15, month = 6, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceForCard(closingDay)

        assertEquals("7.2024", result)
    }

    @Test
    fun `calculateInvoiceForCard should handle year rollover in December`() {
        val currentTime = DomainTime(day = 15, month = 12, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceForCard(closingDay)

        assertEquals("1.2025", result)
    }

    @Test
    fun `calculateInvoiceMonthAndYear should return current month and year when current day is before closing day`() {
        val currentTime = DomainTime(day = 5, month = 6, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceMonthAndYear(closingDay)

        assertEquals(6, result.first)
        assertEquals(2024, result.second)
    }

    @Test
    fun `calculateInvoiceMonthAndYear should return next month and year when current day is after closing day`() {
        val currentTime = DomainTime(day = 15, month = 6, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceMonthAndYear(closingDay)

        assertEquals(7, result.first)
        assertEquals(2024, result.second)
    }

    @Test
    fun `calculateInvoiceMonthAndYear should handle year rollover in December`() {
        val currentTime = DomainTime(day = 15, month = 12, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceMonthAndYear(closingDay)

        assertEquals(1, result.first)
        assertEquals(2025, result.second)
    }

    @Test
    fun `calculateInvoiceMonthAndYear should handle December to January transition when before closing day`() {
        val currentTime = DomainTime(day = 5, month = 12, year = 2024)
        val closingDay = 10

        every { domainTimeRepository.getCurrentDomainTime() } returns currentTime

        val result = invoiceCalculator.calculateInvoiceMonthAndYear(closingDay)

        assertEquals(12, result.first)
        assertEquals(2024, result.second)
    }
}
