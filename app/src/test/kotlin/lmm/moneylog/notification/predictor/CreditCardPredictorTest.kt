package lmm.moneylog.notification.predictor

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class CreditCardPredictorTest {
    private val sharedPreferences = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)
    private lateinit var creditCardPredictor: CreditCardPredictor

    @Before
    fun setup() {
        every { sharedPreferences.edit() } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor
        every { editor.apply() } returns Unit

        creditCardPredictor = CreditCardPredictor(sharedPreferences)
    }

    @Test
    fun `should return credit card id when stored`() {
        every { sharedPreferences.getInt("credit_card_id", -1) } returns 5

        val result = creditCardPredictor.getCreditCardId()

        assertEquals(5, result)
    }

    @Test
    fun `should return null when no credit card id stored`() {
        every { sharedPreferences.getInt("credit_card_id", -1) } returns -1

        val result = creditCardPredictor.getCreditCardId()

        assertNull(result)
    }

    @Test
    fun `should save credit card id correctly`() {
        creditCardPredictor.saveCreditCardId(10)

        verify { editor.putInt("credit_card_id", 10) }
        verify { editor.apply() }
    }

    @Test
    fun `should remove credit card id correctly`() {
        creditCardPredictor.removeCreditCardId()

        verify { editor.remove("credit_card_id") }
        verify { editor.apply() }
    }

    @Test
    fun `should handle multiple save operations`() {
        creditCardPredictor.saveCreditCardId(1)
        creditCardPredictor.saveCreditCardId(2)

        verify { editor.putInt("credit_card_id", 1) }
        verify { editor.putInt("credit_card_id", 2) }
        verify(exactly = 2) { editor.apply() }
    }
}
