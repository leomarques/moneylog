package lmm.moneylog.data.demo.repositories

import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository

class InMemoryNotificationSettingsRepository : NotificationSettingsRepository {
    private var defaultCreditCardId: Int? = null
    private var defaultCategoryId: Int? = null

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
        defaultCreditCardId = null
        defaultCategoryId = null
    }
}
