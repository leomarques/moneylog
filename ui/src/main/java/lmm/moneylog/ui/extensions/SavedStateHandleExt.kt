package lmm.moneylog.ui.extensions

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.getIdParam(): Int? {
    val id = get<Int>("id") ?: return null
    return if (id != -1) id else null
}
