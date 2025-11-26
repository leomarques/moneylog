package lmm.moneylog.data.transaction.nubank.converter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import lmm.moneylog.data.creditcard.utils.InvoiceCalculator
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class NubankTransactionConverterImplTest {
    private lateinit var domainTimeRepository: DomainTimeRepository
    private lateinit var invoiceCalculator: InvoiceCalculator
    private lateinit var converter: NubankTransactionConverterImpl

    private val mockCurrentTime = DomainTime(day = 15, month = 6, year = 2024)

    @Before
    fun setup() {
        domainTimeRepository = mockk()
        invoiceCalculator = mockk()
        converter = NubankTransactionConverterImpl(domainTimeRepository, invoiceCalculator)

        every { domainTimeRepository.getCurrentDomainTime() } returns mockCurrentTime
    }

    @Test
    fun `convert should return null when value parsing fails`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "invalid",
                    place = "Test Place",
                    currency = "R$"
                )

            val result = converter.convert(transactionInfo)

            assertNull(result)
        }

    @Test
    fun `convert should return null when value is empty`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "",
                    place = "Test Place",
                    currency = "R$"
                )

            val result = converter.convert(transactionInfo)

            assertNull(result)
        }

    @Test
    fun `convert should parse Brazilian currency format correctly`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 1.234,56",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-1234.56, result!!.value, 0.01)
            assertEquals("Test Place", result.description)
            assertEquals(mockCurrentTime, result.date)
            assertEquals(1, result.categoryId)
            assertNull(result.accountId)
            assertNull(result.creditCardId)
            assertNull(result.invoiceMonth)
            assertNull(result.invoiceYear)
        }

    @Test
    fun `convert should parse US currency format correctly`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "1234.56",
                    place = "Test Place",
                    currency = "$",
                    categoryId = 2
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-1234.56, result!!.value, 0.01)
            assertEquals("Test Place", result.description)
            assertEquals(2, result.categoryId)
        }

    @Test
    fun `convert should calculate invoice when credit card info is provided`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1,
                    creditCardId = 123,
                    creditCardClosingDay = 10
                )

            every {
                invoiceCalculator.calculateInvoiceMonthAndYear(10)
            } returns Pair(7, 2024)

            val result = converter.convert(transactionInfo)

            assertEquals(-100.0, result!!.value, 0.01)
            assertEquals(123, result.creditCardId)
            assertEquals(7, result.invoiceMonth)
            assertEquals(2024, result.invoiceYear)

            verify { invoiceCalculator.calculateInvoiceMonthAndYear(10) }
        }

    @Test
    fun `convert should not calculate invoice when credit card id is null`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1,
                    creditCardId = null,
                    creditCardClosingDay = 10
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-100.0, result!!.value, 0.01)
            assertNull(result.creditCardId)
            assertNull(result.invoiceMonth)
            assertNull(result.invoiceYear)

            verify(exactly = 0) { invoiceCalculator.calculateInvoiceMonthAndYear(any()) }
        }

    @Test
    fun `convert should not calculate invoice when closing day is null`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1,
                    creditCardId = 123,
                    creditCardClosingDay = null
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-100.0, result!!.value, 0.01)
            assertEquals(123, result.creditCardId)
            assertNull(result.invoiceMonth)
            assertNull(result.invoiceYear)

            verify(exactly = 0) { invoiceCalculator.calculateInvoiceMonthAndYear(any()) }
        }

    @Test
    fun `convert should handle different currency symbols`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "€ 50,75",
                    place = "Test Place",
                    currency = "€",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-50.75, result!!.value, 0.01)
        }

    @Test
    fun `convert should handle values with spaces and extra characters`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "  R$ 1.500,99  ",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertEquals(-1500.99, result!!.value, 0.01)
        }

    @Test
    fun `convert should return null when exception occurs in invoice calculation`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1,
                    creditCardId = 123,
                    creditCardClosingDay = 10
                )

            every {
                invoiceCalculator.calculateInvoiceMonthAndYear(10)
            } throws RuntimeException("Test exception")

            val result = converter.convert(transactionInfo)

            assertNull(result)
        }

    @Test
    fun `convert should return null when domain time repository throws exception`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$"
                )

            every { domainTimeRepository.getCurrentDomainTime() } throws RuntimeException("Test exception")

            val result = converter.convert(transactionInfo)

            assertNull(result)
        }

    @Test
    fun `convert should handle zero values`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 0,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertEquals(0.0, result!!.value, 0.01)
            assertEquals("Test Place", result.description)
            assertEquals(1, result.categoryId)
        }

    @Test
    fun `convert should handle negative values in input`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ -50,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertEquals(50.0, result!!.value, 0.01)
        }

    @Test
    fun `convert should always set accountId to null`() =
        runTest {
            val transactionInfo =
                NubankTransactionInfo(
                    value = "R$ 100,00",
                    place = "Test Place",
                    currency = "R$",
                    categoryId = 1
                )

            val result = converter.convert(transactionInfo)

            assertNull(result!!.accountId)
        }
}
