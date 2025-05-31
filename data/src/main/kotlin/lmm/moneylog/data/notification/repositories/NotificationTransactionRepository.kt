package lmm.moneylog.data.notification.repositories

interface NotificationTransactionRepository {
    suspend fun storeTransactionId(
        notificationId: Int,
        transactionId: Long
    )

    suspend fun getTransactionId(notificationId: Int): Long?

    suspend fun removeTransactionId(notificationId: Int)

    suspend fun clearAllTransactionIds()
}
