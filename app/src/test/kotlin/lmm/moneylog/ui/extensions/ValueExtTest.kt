package lmm.moneylog.ui.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValueExtTest {
    @Test
    fun testFormatForRs() {
        assertEquals("R$1.234.567,89", 1234567.89.formatForRs())

        assertEquals("R$100,00", 100.0.formatForRs())

        assertEquals("R$1.000,00", 1000.0.formatForRs())

        assertEquals("R$10.000,00", 10000.0.formatForRs())

        assertEquals("R$0,10", 0.1.formatForRs())

        assertEquals("R$0,01", 0.01.formatForRs())

        assertEquals("R$1,23", 1.23.formatForRs())

        assertEquals("R$12.345,67", 12345.67.formatForRs())

        assertEquals("R$123.456,78", 123456.78.formatForRs())

        assertEquals("R$1.234.567,00", 1234567.0.formatForRs())

        assertEquals("R$0,00", 0.0.formatForRs())
    }
}
