package lmm.moneylog.ui.extensions

import java.util.Locale.getDefault

fun String.validateValue(isIncome: Boolean = true): Double {
    val value = replace(",", ".").toDouble()
    if (value <= 0.0) {
        throw java.lang.NumberFormatException("Negative number")
    }

    return if (isIncome) {
        value
    } else {
        -value
    }
}

fun Double.formatForRs(allowNegative: Boolean = true): String {
    val absValue = kotlin.math.abs(this)
    val formattedNumber = String.format(getDefault(), "%.2f", absValue).replace('.', ',')
    val parts = formattedNumber.split(',')
    val reversed = parts[0].reversed()
    val withDots = reversed.chunked(3).joinToString(".")
    val result = withDots.reversed() + "," + parts[1]
    return if (this < 0 && allowNegative) "R$-$result" else "R$$result"
}
