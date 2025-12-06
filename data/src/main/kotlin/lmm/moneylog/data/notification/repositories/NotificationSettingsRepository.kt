package lmm.moneylog.data.notification.repositories

interface NotificationSettingsRepository {
    fun getDefaultCreditCardId(): Int?

    fun saveDefaultCreditCardId(creditCardId: Int)

    fun removeDefaultCreditCardId()

    fun getDefaultCategoryId(): Int?

    fun saveDefaultCategoryId(categoryId: Int)

    fun removeDefaultCategoryId()
}
