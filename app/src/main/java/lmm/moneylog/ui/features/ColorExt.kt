package lmm.moneylog.ui.features

import androidx.compose.ui.graphics.Color

fun Long.toColor() = Color(this.toULong())
