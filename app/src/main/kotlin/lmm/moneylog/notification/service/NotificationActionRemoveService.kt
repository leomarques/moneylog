package lmm.moneylog.notification.service

import android.app.IntentService
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import lmm.moneylog.data.notification.repositories.NotificationTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class NotificationActionRemoveService : IntentService("NotificationActionService") {
    companion object {
        const val ACTION_REMOVE_TRANSACTION = "ACTION_REMOVE_TRANSACTION"
        const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
    }

    private val notificationTransactionRepository: NotificationTransactionRepository by inject()
    private val deleteTransactionRepository: DeleteTransactionRepository by inject()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            when (it.action) {
                ACTION_REMOVE_TRANSACTION -> {
                    val notificationId = it.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
                    if (notificationId != -1) {
                        handleRemoveTransaction(notificationId)
                    }
                }
            }
        }
    }

    private fun handleRemoveTransaction(notificationId: Int) {
        scope.launch {
            try {
                val transactionId = notificationTransactionRepository.getTransactionId(notificationId)
                transactionId?.let { id ->
                    deleteTransactionRepository.delete(id.toInt())
                    notificationTransactionRepository.removeTransactionId(notificationId)
                }
            } catch (e: Exception) {
            }
        }
    }
}
