package lmm.moneylog.ui.extensions

import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.theme.neutralColor

fun Long.toComposeColor() = Color(this.toULong())

fun Color?.orDefaultColor(): Color {
    return this ?: neutralColor
}
