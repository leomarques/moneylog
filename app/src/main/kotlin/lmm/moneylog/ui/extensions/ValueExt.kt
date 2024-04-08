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

fun Double.formatForRs(): String {
    // Convert the number to a string, replacing '.' with ',' and formatting to two decimal places
    val formattedNumber = String.format(getDefault(), "%.2f", this).replace('.', ',')

    // Split the string into two parts: before and after the decimal separator
    val parts = formattedNumber.split(',')

    // Reverse the part before the decimal separator to facilitate inserting dots every three characters
    val reversed = parts[0].reversed()

    // Insert a dot every three characters
    val withDots = reversed.chunked(3).joinToString(".")

    // Reverse back and combine with the part after the decimal separator
    val result = withDots.reversed() + "," + parts[1]

    // Return the formatted string with the "R$" prefix
    return "R$$result"
}
