package lmm.moneylog.data.demo.repositories

import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository

class InMemoryNotificationSettingsRepository : NotificationSettingsRepository {
    private var notificationInterceptionEnabled: Boolean = true
    private var defaultCreditCardId: Int? = null
    private var defaultCategoryId: Int? = null

    override fun isNotificationInterceptionEnabled(): Boolean = notificationInterceptionEnabled

    override fun setNotificationInterceptionEnabled(enabled: Boolean) {
        notificationInterceptionEnabled = enabled
    }

    override fun getDefaultCreditCardId(): Int? = defaultCreditCardId

    override fun saveDefaultCreditCardId(creditCardId: Int) {
        defaultCreditCardId = creditCardId
    }

    override fun removeDefaultCreditCardId() {
        defaultCreditCardId = null
    }

    override fun getDefaultCategoryId(): Int? = defaultCategoryId

    override fun saveDefaultCategoryId(categoryId: Int) {
        defaultCategoryId = categoryId
    }

    override fun removeDefaultCategoryId() {
        defaultCategoryId = null
    }

    internal fun clear() {
        notificationInterceptionEnabled = true
        defaultCreditCardId = null
        defaultCategoryId = null
    }
}
