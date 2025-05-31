package lmm.moneylog.notification.parser

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import lmm.moneylog.notification.predictor.CreditCardPredictor
import org.junit.Before
import org.junit.Test

class NubankTransactionParserTest {
    private val creditCardPredictor = mockk<CreditCardPredictor>()
    private lateinit var parser: NubankTransactionParser

    @Before
    fun setup() {
        parser = NubankTransactionParser(creditCardPredictor)
    }

    @Test
    fun `should parse valid transaction notification correctly`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("60,89", result.value)
        TestCase.assertEquals("IFD*RESTAURANTE BAKO S", result.place)
    }

    @Test
    fun `should parse transaction with USD currency`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de US$ 25,50 APROVADA em AMAZON WEB SERVICES para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("US$", result!!.currency)
        TestCase.assertEquals("25,50", result.value)
        TestCase.assertEquals("AMAZON WEB SERVICES", result.place)
    }

    @Test
    fun `should parse transaction with large value`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 1.234,56 APROVADA em LOJA DEPARTAMENTOS para o cartão com final 9876"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("1.234,56", result.value)
        TestCase.assertEquals("LOJA DEPARTAMENTOS", result.place)
    }

    @Test
    fun `should parse transaction with special characters in place name`() {
        every { creditCardPredictor.getCreditCardId() } returns null

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
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "compra de r$ 50,00 aprovada em supermercado xyz para o cartão com final 1111"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("r$", result!!.currency)
        TestCase.assertEquals("50,00", result.value)
        TestCase.assertEquals("supermercado xyz", result.place)
    }

    @Test
    fun `should parse transaction with integer value`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 100 APROVADA em POSTO GASOLINA para o cartão com final 2222"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals("R$", result!!.currency)
        TestCase.assertEquals("100", result.value)
        TestCase.assertEquals("POSTO GASOLINA", result.place)
    }

    @Test
    fun `should predict category for IFD merchant`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(0, result!!.categoryId)
    }

    @Test
    fun `should predict category for IFD merchant case insensitive`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 25,30 APROVADA em ifd*lanchonete xyz para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(0, result!!.categoryId)
    }

    @Test
    fun `should return null categoryId for unknown merchant`() {
        every { creditCardPredictor.getCreditCardId() } returns null

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
    fun `should predict category when IFD is part of longer merchant name`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 75,50 APROVADA em SOME_IFD*COMPLEX_MERCHANT_NAME para o cartão com final 7777"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(0, result!!.categoryId)
    }

    @Test
    fun `should get credit card id from shared preferences`() {
        every { creditCardPredictor.getCreditCardId() } returns 5

        val text = "Compra de R$ 60,89 APROVADA em RESTAURANTE ABC para o cartão com final 5794"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(5, result!!.creditCardId)
    }

    @Test
    fun `should return null creditCardId when none stored`() {
        every { creditCardPredictor.getCreditCardId() } returns null

        val text = "Compra de R$ 25,50 APROVADA em LOJA XYZ para o cartão com final 1234"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertNull(result!!.creditCardId)
    }

    @Test
    fun `should work without card ending in notification text`() {
        every { creditCardPredictor.getCreditCardId() } returns 3

        val text = "Compra de R$ 60,89 APROVADA em IFD*RESTAURANTE BAKO S para o cartão"

        val result = parser.parseTransactionInfo(text)

        TestCase.assertNotNull(result)
        TestCase.assertEquals(3, result!!.creditCardId)
        TestCase.assertEquals(0, result.categoryId)
        TestCase.assertEquals("IFD*RESTAURANTE BAKO S", result.place)
    }
}
