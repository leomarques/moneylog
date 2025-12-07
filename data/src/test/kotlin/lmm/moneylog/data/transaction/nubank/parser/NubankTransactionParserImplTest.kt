package lmm.moneylog.data.transaction.nubank.parser

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository
import org.junit.Before
import org.junit.Test

class NubankTransactionParserImplTest {
    private val notificationSettingsRepository = mockk<NotificationSettingsRepository>()
    private val getCreditCardsRepository = mockk<GetCreditCardsRepository>()
    private val categoryKeywordRepository = mockk<CategoryKeywordRepository>()
    private lateinit var parser: NubankTransactionParserImpl

    @Before
    fun setup() {
        // By default, return null from keyword repository (no match)
        coEvery { categoryKeywordRepository.predictCategory(any()) } returns null
        // By default, return null from default category ID (no default set)
        every { notificationSettingsRepository.getDefaultCategoryId() } returns null
        parser =
            NubankTransactionParserImpl(
                notificationSettingsRepository,
                getCreditCardsRepository,
                categoryKeywordRepository
            )
    }

    @Test
    fun `should parse valid transaction notification correctly`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text =
            "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("60,89", result.value)
        TestCase.assertEquals("IFD*RESTAURANTE BAKO S", result.place)
    }

    @Test
    fun `should parse transaction with USD currency`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text =
            "Compra de US$ 25,50 APROVADA em AMAZON WEB SERVICES para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("US$", result!!.currency)
        TestCase.assertEquals("25,50", result.value)
        TestCase.assertEquals("AMAZON WEB SERVICES", result.place)
    }

    @Test
    fun `should parse transaction with large value`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text =
            "Compra de R$ 1.234,56 APROVADA em LOJA DEPARTAMENTOS para o cartão com final 9876"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("1.234,56", result.value)
        TestCase.assertEquals("LOJA DEPARTAMENTOS", result.place)
    }

    @Test
    fun `should parse transaction with special characters in place name`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text = "Compra de R$ 15,99 APROVADA em MC DONALD'S & CO para o cartão com final 5555"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("15,99", result.value)
        TestCase.assertEquals("MC DONALD'S & CO", result.place)
    }

    @Test
    fun `should return null for invalid transaction format`() {
        val text = "Pagamento de R$ 100,00 realizado com sucesso"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNull(result)
    }

    @Test
    fun `should return null for empty text`() {
        val result = parser.parseTransactionInfo("")

        TestCase.assertNull(result)
    }

    @Test
    fun `should return null for blank text`() {
        val result = parser.parseTransactionInfo("   ")

        TestCase.assertNull(result)
    }

    @Test
    fun `should handle case insensitive matching`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text = "compra de r$ 50,00 aprovada em supermercado xyz para o cartão com final 1111"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("r$", result!!.currency)
        TestCase.assertEquals("50,00", result.value)
        TestCase.assertEquals("supermercado xyz", result.place)
    }

    @Test
    fun `should parse transaction with integer value`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text = "Compra de R$ 100 APROVADA em POSTO GASOLINA para o cartão com final 2222"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("100", result.value)
        TestCase.assertEquals("POSTO GASOLINA", result.place)
    }

    @Test
    fun `should predict category from keyword repository`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null
        coEvery { categoryKeywordRepository.predictCategory("IFD*RESTAURANTE BAKO S") } returns 1

        val text =
            "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(1, result!!.categoryId)
    }

    @Test
    fun `should predict category case insensitive from keyword repository`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null
        coEvery { categoryKeywordRepository.predictCategory("ifd*lanchonete xyz") } returns 1

        val text = "Compra de R$ 25,30 APROVADA em ifd*lanchonete xyz para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(1, result!!.categoryId)
    }

    @Test
    fun `should return null categoryId for unknown merchant`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text = "Compra de R$ 50,00 APROVADA em SUPERMERCADO ABC para o cartão com final 5555"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertNull(result!!.categoryId)
    }

    @Test
    fun `should return null for empty place`() {
        val text = "Compra de R$ 100,00 APROVADA em  para o cartão com final 9999"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNull(result)
    }

    @Test
    fun `should predict category when keyword is part of longer merchant name`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null
        coEvery { categoryKeywordRepository.predictCategory("SOME_IFD*COMPLEX_MERCHANT_NAME") } returns 1

        val text =
            "Compra de R$ 75,50 APROVADA em SOME_IFD*COMPLEX_MERCHANT_NAME para o cartão com final 7777"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(1, result!!.categoryId)
    }

    @Test
    fun `should get credit card id from shared preferences`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns 5
        coEvery { getCreditCardsRepository.getCreditCardById(5) } returns
            CreditCard(
                id = 5,
                name = "Test Card",
                closingDay = 15,
                dueDay = 25,
                limit = 10000,
                color = 0xFF0000FF
            )

        val text = "Compra de R$ 60,89 APROVADA em RESTAURANTE ABC para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(5, result!!.creditCardId)
        TestCase.assertEquals(15, result.creditCardClosingDay)
    }

    @Test
    fun `should return null creditCardId when none stored`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null

        val text = "Compra de R$ 25,50 APROVADA em LOJA XYZ para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertNull(result!!.creditCardId)
    }

    @Test
    fun `should work without card ending in notification text`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns 3
        coEvery { getCreditCardsRepository.getCreditCardById(3) } returns
            CreditCard(
                id = 3,
                name = "Test Card 2",
                closingDay = 10,
                dueDay = 20,
                limit = 5000,
                color = 0xFF00FF00
            )
        coEvery { categoryKeywordRepository.predictCategory("IFD*RESTAURANTE BAKO S") } returns 1

        val text = "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(3, result!!.creditCardId)
        TestCase.assertEquals(10, result.creditCardClosingDay)
        TestCase.assertEquals(1, result.categoryId)
        TestCase.assertEquals("IFD*RESTAURANTE BAKO S", result.place)
    }

    @Test
    fun `should use keyword-based category prediction when available`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null
        coEvery { categoryKeywordRepository.predictCategory("UBER EATS") } returns 5

        val text = "Compra de R$ 35,50 APROVADA em UBER EATS para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(5, result!!.categoryId)
    }

    @Test
    fun `should return null when keyword repository returns null`() {
        every { notificationSettingsRepository.getDefaultCreditCardId() } returns null
        coEvery { categoryKeywordRepository.predictCategory("UNKNOWN MERCHANT") } returns null

        val text = "Compra de R$ 25,00 APROVADA em UNKNOWN MERCHANT para o cartão com final 5555"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertNull(result!!.categoryId)
    }
}
