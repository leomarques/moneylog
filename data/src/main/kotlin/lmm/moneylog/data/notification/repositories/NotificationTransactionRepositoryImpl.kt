package lmm.moneylog.data.notification.repositories

import android.content.SharedPreferences

class NotificationTransactionRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : NotificationTransactionRepository {
    companion object {
        private const val NOTIFICATION_TRANSACTION_PREFIX = "notification_transaction_"
    }

    override suspend fun storeTransactionId(
        notificationId: Int,
        transactionId: Long
    ) {
        sharedPreferences
            .edit()
            .putLong("$NOTIFICATION_TRANSACTION_PREFIX$notificationId", transactionId)
            .apply()
    }

    override suspend fun getTransactionId(notificationId: Int): Long? {
        val transactionId = sharedPreferences.getLong("$NOTIFICATION_TRANSACTION_PREFIX$notificationId", -1L)
        return if (transactionId == -1L) null else transactionId
    }

    override suspend fun removeTransactionId(notificationId: Int) {
        sharedPreferences
            .edit()
            .remove("$NOTIFICATION_TRANSACTION_PREFIX$notificationId")
            .apply()
    }

    override suspend fun clearAllTransactionIds() {
        val editor = sharedPreferences.edit()
        sharedPreferences.all.keys
            .filter { it.startsWith(NOTIFICATION_TRANSACTION_PREFIX) }
            .forEach { editor.remove(it) }
        editor.apply()
    }
}
