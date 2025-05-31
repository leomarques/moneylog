package lmm.moneylog.notification.predictor

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class CategoryPredictorTest {
    @Test
    fun `should predict category for exact IFD match`() {
        val result = CategoryPredictor.predictCategory("IFD*RESTAURANTE")
        assertEquals(0, result)
    }

    @Test
    fun `should predict category for IFD case insensitive`() {
        val result = CategoryPredictor.predictCategory("ifd*lanchonete")
        assertEquals(0, result)
    }

    @Test
    fun `should predict category for uppercase IFD`() {
        val result = CategoryPredictor.predictCategory("IFD*FAST FOOD")
        assertEquals(0, result)
    }

    @Test
    fun `should predict category for IFD with extra spaces`() {
        val result = CategoryPredictor.predictCategory("  IFD*RESTAURANT NAME  ")
        assertEquals(0, result)
    }

    @Test
    fun `should predict category when IFD is in middle of string`() {
        val result = CategoryPredictor.predictCategory("SOME PREFIX IFD*MERCHANT SUFFIX")
        assertEquals(0, result)
    }

    @Test
    fun `should return null for unknown merchant`() {
        val result = CategoryPredictor.predictCategory("UNKNOWN MERCHANT")
        assertNull(result)
    }

    @Test
    fun `should return null for empty string`() {
        val result = CategoryPredictor.predictCategory("")
        assertNull(result)
    }

    @Test
    fun `should return null for blank string`() {
        val result = CategoryPredictor.predictCategory("   ")
        assertNull(result)
    }

    @Test
    fun `should return null for partial IFD match`() {
        val result = CategoryPredictor.predictCategory("IF MERCHANT")
        assertNull(result)
    }

    @Test
    fun `should return null for IFD without asterisk`() {
        val result = CategoryPredictor.predictCategory("IFD MERCHANT")
        assertNull(result)
    }

    @Test
    fun `should handle special characters in merchant name`() {
        val result = CategoryPredictor.predictCategory("IFD*MC DONALD'S & CO")
        assertEquals(0, result)
    }

    @Test
    fun `should handle numbers in merchant name`() {
        val result = CategoryPredictor.predictCategory("IFD*RESTAURANT 123")
        assertEquals(0, result)
    }
}
