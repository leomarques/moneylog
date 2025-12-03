package lmm.moneylog.misc.nubank

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NubankNotificationActionReceiver : BroadcastReceiver(), KoinComponent {
    companion object {
        const val ACTION_REMOVE_TRANSACTION =
            "lmm.moneylog.ACTION_REMOVE_TRANSACTION"
        const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
        const val EXTRA_TRANSACTION_ID = "EXTRA_TRANSACTION_ID"
    }

    private val deleteTransactionRepository: DeleteTransactionRepository by inject()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        if (context == null || intent == null) return

        when (intent.action) {
            ACTION_REMOVE_TRANSACTION -> {
                val transactionId = intent.getIntExtra(EXTRA_TRANSACTION_ID, -1)
                val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
                if (transactionId != -1) {
                    handleRemoveTransaction(
                        context = context,
                        transactionId = transactionId,
                        notificationId = notificationId
                    )
                }
            }
        }
    }

    private fun handleRemoveTransaction(
        context: Context,
        transactionId: Int,
        notificationId: Int
    ) {
        // Use goAsync() to ensure the BroadcastReceiver stays alive during the async operation
        val pendingResult = goAsync()

        scope.launch {
            try {
                deleteTransactionRepository.delete(transactionId)

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(notificationId)
            } catch (_: Exception) {
                // Silent failure as per original implementation
            } finally {
                pendingResult.finish()
            }
        }
    }
}
