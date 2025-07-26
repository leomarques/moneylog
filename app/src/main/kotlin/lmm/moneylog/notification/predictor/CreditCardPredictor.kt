package lmm.moneylog.notification.predictor

import android.content.SharedPreferences
import androidx.core.content.edit

class CreditCardPredictor(
    private val sharedPreferences: SharedPreferences
) {
    fun getCreditCardId(): Int? = sharedPreferences.getInt(CREDIT_CARD_ID_KEY, -1).takeIf { it != -1 }

    fun saveCreditCardId(creditCardId: Int) {
        sharedPreferences.edit {
            putInt(CREDIT_CARD_ID_KEY, creditCardId)
        }
    }

    fun removeCreditCardId() {
        sharedPreferences.edit {
            remove(CREDIT_CARD_ID_KEY)
        }
    }

    companion object {
        private const val CREDIT_CARD_ID_KEY = "credit_card_id"
    }
}
