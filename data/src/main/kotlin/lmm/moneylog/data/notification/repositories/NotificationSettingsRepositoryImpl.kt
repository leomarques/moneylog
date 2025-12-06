package lmm.moneylog.data.notification.repositories

import android.content.SharedPreferences

class NotificationSettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : NotificationSettingsRepository {
    override fun getDefaultCreditCardId(): Int? =
        sharedPreferences.getInt(DEFAULT_CREDIT_CARD_ID_KEY, -1).takeIf { it != -1 }

    override fun saveDefaultCreditCardId(creditCardId: Int) {
        sharedPreferences
            .edit()
            .putInt(DEFAULT_CREDIT_CARD_ID_KEY, creditCardId)
            .apply()
    }

    override fun removeDefaultCreditCardId() {
        sharedPreferences
            .edit()
            .remove(DEFAULT_CREDIT_CARD_ID_KEY)
            .apply()
    }

    override fun getDefaultCategoryId(): Int? =
        sharedPreferences.getInt(DEFAULT_CATEGORY_ID_KEY, -1).takeIf { it != -1 }

    override fun saveDefaultCategoryId(categoryId: Int) {
        sharedPreferences
            .edit()
            .putInt(DEFAULT_CATEGORY_ID_KEY, categoryId)
            .apply()
    }

    override fun removeDefaultCategoryId() {
        sharedPreferences
            .edit()
            .remove(DEFAULT_CATEGORY_ID_KEY)
            .apply()
    }

    companion object {
        private const val DEFAULT_CREDIT_CARD_ID_KEY = "notification_default_credit_card_id"
        private const val DEFAULT_CATEGORY_ID_KEY = "notification_default_category_id"
    }
}
