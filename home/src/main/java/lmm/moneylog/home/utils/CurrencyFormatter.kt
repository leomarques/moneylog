package lmm.moneylog.home.utils

import java.util.Locale
import kotlin.math.abs

/**
 * Formats a Double value to Brazilian Real currency format (R$)
 *
 * @param allowNegative Whether to allow negative values
 * @return Formatted currency string
 */
fun Double.formatToBRL(allowNegative: Boolean = true): String {
    val absValue = abs(this)
    val formattedNumber = String.format(Locale.getDefault(), "%.2f", absValue).replace('.', ',')
    val parts = formattedNumber.split(',')
    val reversed = parts[0].reversed()
    val withDots = reversed.chunked(3).joinToString(".")
    val result = withDots.reversed() + "," + parts[1]
    return if (this < 0 && allowNegative) "R$-$result" else "R$$result"
}
