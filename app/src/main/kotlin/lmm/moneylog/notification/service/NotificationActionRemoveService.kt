package lmm.moneylog.notification.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class NotificationActionRemoveService : IntentService("NotificationActionService") {
    companion object {
        const val ACTION_REMOVE_TRANSACTION = "ACTION_REMOVE_TRANSACTION"
        const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
        const val EXTRA_TRANSACTION_ID = "EXTRA_TRANSACTION_ID"
    }

    private val deleteTransactionRepository: DeleteTransactionRepository by inject()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            when (it.action) {
                ACTION_REMOVE_TRANSACTION -> {
                    val transactionId = it.getIntExtra(EXTRA_TRANSACTION_ID, -1)
                    val notificationId = it.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
                    if (transactionId != -1) {
                        handleRemoveTransaction(
                            transactionId = transactionId,
                            notificationId = notificationId
                        )
                    }
                }
            }
        }
    }

    private fun handleRemoveTransaction(
        transactionId: Int,
        notificationId: Int
    ) {
        scope.launch {
            try {
                deleteTransactionRepository.delete(transactionId)

                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(notificationId)
            } catch (e: Exception) {
            }
        }
    }
}
