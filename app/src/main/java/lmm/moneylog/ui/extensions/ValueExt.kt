package lmm.moneylog.ui.extensions

fun String.validateValue(isIncome: Boolean = true): Double {
    val value = toDouble()
    if (value <= 0.0) {
        throw java.lang.NumberFormatException("Negative number")
    }

    return if (isIncome) {
        value
    } else {
        -value
    }
}

fun Double.formatForRs() = "R\$%.2f".format(this)
