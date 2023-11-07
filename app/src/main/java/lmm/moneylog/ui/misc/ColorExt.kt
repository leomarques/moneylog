package lmm.moneylog.ui.misc

import androidx.compose.ui.graphics.Color

fun Long.toColor() = Color(this.toULong())
