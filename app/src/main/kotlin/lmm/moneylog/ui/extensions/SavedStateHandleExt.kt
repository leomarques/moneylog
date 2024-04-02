package lmm.moneylog.ui.extensions

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.getIdParam(): Int? {
    get<Int>("id")?.let { id ->
        return if (id != -1) id else null
    } ?: return null
}
