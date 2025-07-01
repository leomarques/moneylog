package lmm.moneylog.notification.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.notification.repositories.NotificationTransactionRepository
import lmm.moneylog.notification.config.NotificationConfig
import lmm.moneylog.notification.model.NotificationTransactionInfo
import lmm.moneylog.notification.service.NotificationActionRemoveService
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationHelper(private val context: NotificationListenerService) : NotificationDisplayer, KoinComponent {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private fun createNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                NotificationConfig.Channel.ID,
                NotificationConfig.Channel.NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationManager.createNotificationChannel(channel)
    }

    override fun showNotification(
        title: String?,
        transactionInfo: NotificationTransactionInfo,
        transactionId: Long?
    ) {
        createNotificationChannel()

        val notificationText = formatNotificationText(transactionInfo)
        val notificationId = System.currentTimeMillis().toInt()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder =
            NotificationCompat.Builder(context, NotificationConfig.Channel.ID)
                .setSmallIcon(R.drawable.outline_attach_money_24)
                .setContentTitle(sanitizeTitle(title))
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        if (transactionId != null) {
            val removeIntent =
                Intent(context, NotificationActionRemoveService::class.java).apply {
                    action = NotificationActionRemoveService.ACTION_REMOVE_TRANSACTION
                    putExtra(NotificationActionRemoveService.EXTRA_NOTIFICATION_ID, notificationId)
                    putExtra(NotificationActionRemoveService.EXTRA_NOTIFICATION_ID, transactionId)
                }

            val removePendingIntent =
                PendingIntent.getService(
                    context,
                    notificationId,
                    removeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            builder.addAction(
                android.R.drawable.ic_menu_delete,
                "Remover",
                removePendingIntent
            )

            val openAppIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(NotificationActionRemoveService.EXTRA_TRANSACTION_ID, transactionId)
            }

            val openAppPendingIntent = PendingIntent.getActivity(
                context,
                0,
                openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            builder.setContentIntent(openAppPendingIntent)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    private fun formatNotificationText(transactionInfo: NotificationTransactionInfo): String {
        return "R$ ${transactionInfo.value} em ${transactionInfo.place}"
    }

    private fun sanitizeTitle(title: String?): String {
        return title?.trim()
            ?.take(100)
            ?.takeIf { it.isNotBlank() } ?: "MoneyLog Transaction"
    }
}
