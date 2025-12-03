package lmm.moneylog.ui.components.misc

import android.content.Context
import android.widget.Toast

fun showToast(
    current: Context,
    stringId: Int
) {
    Toast
        .makeText(
            current,
            current.getString(stringId),
            Toast.LENGTH_LONG
        ).show()
}
