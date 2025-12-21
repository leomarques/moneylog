package lmm.moneylog.ui.extensions

import java.lang.NumberFormatException
import java.util.Locale.getDefault
import kotlin.math.abs
import kotlin.math.round

fun String.validateValue(isIncome: Boolean = true): Double {
    val value = replace(",", ".").toDouble()
    if (value <= 0.0) {
        throw NumberFormatException("Negative number")
    }

    return if (isIncome) {
        value
    } else {
        -value
    }
}

fun Double.roundTo2Decimals(): Double = round(this * 100) / 100

fun Double.formatForRs(allowNegative: Boolean = true): String {
    val absValue = abs(this)
    val formattedNumber = String.format(getDefault(), "%.2f", absValue).replace('.', ',')
    val parts = formattedNumber.split(',')
    val reversed = parts[0].reversed()
    val withDots = reversed.chunked(3).joinToString(".")
    val result = withDots.reversed() + "," + parts[1]
    return if (this < 0 && allowNegative) "R$-$result" else "R$$result"
}
