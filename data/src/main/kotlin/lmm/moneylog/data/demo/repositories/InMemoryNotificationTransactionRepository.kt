package lmm.moneylog.data.demo.repositories

import lmm.moneylog.data.notification.repositories.NotificationTransactionRepository

class InMemoryNotificationTransactionRepository : NotificationTransactionRepository {
    private val transactionIds = mutableMapOf<Int, Long>()

    override suspend fun storeTransactionId(notificationId: Int, transactionId: Long) {
        transactionIds[notificationId] = transactionId
    }

    override suspend fun getTransactionId(notificationId: Int): Long? =
        transactionIds[notificationId]

    override suspend fun removeTransactionId(notificationId: Int) {
        transactionIds.remove(notificationId)
    }

    override suspend fun clearAllTransactionIds() {
        transactionIds.clear()
    }

    internal fun clear() {
        transactionIds.clear()
    }
}
