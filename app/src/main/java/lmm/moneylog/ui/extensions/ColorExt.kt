package lmm.moneylog.ui.extensions

import androidx.compose.ui.graphics.Color

fun Long.toComposeColor() = Color(this.toULong())
