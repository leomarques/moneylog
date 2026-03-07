package lmm.moneylog.data.notification.repositories

interface NotificationSettingsRepository {
    fun isNotificationInterceptionEnabled(): Boolean

    fun setNotificationInterceptionEnabled(enabled: Boolean)

    fun getDefaultCreditCardId(): Int?

    fun saveDefaultCreditCardId(creditCardId: Int)

    fun removeDefaultCreditCardId()

    fun getDefaultCategoryId(): Int?

    fun saveDefaultCategoryId(categoryId: Int)

    fun removeDefaultCategoryId()
}
