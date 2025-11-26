package lmm.moneylog.ui.features.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import androidx.core.app.NotificationCompat
import lmm.moneylog.R
import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo
import lmm.moneylog.services.nubank.NubankNotificationActionService
import lmm.moneylog.ui.navigation.misc.MainActivity
import org.koin.core.component.KoinComponent

class NotificationHelper(
    private val context: NotificationListenerService
) : NotificationDisplayer,
    KoinComponent {
    private fun createNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationManager.createNotificationChannel(channel)
    }

    override fun showNotification(
        title: String?,
        transactionInfo: NubankTransactionInfo,
        transactionId: Long?
    ) {
        createNotificationChannel()

        val notificationText = formatNotificationText(transactionInfo)
        val notificationId = System.currentTimeMillis().toInt()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.outline_attach_money_24)
                .setContentTitle(sanitizeTitle(title))
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        if (transactionId != null) {
            val removeIntent =
                Intent(context, NubankNotificationActionService::class.java).apply {
                    action = NubankNotificationActionService.ACTION_REMOVE_TRANSACTION
                    putExtra(NubankNotificationActionService.EXTRA_NOTIFICATION_ID, notificationId)
                    putExtra(NubankNotificationActionService.EXTRA_TRANSACTION_ID, transactionId)
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

            val openAppIntent =
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }

            val openAppPendingIntent =
                PendingIntent.getActivity(
                    context,
                    0,
                    openAppIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            builder.setContentIntent(openAppPendingIntent)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    private fun formatNotificationText(transactionInfo: NubankTransactionInfo): String =
        "R$ ${transactionInfo.value} em ${transactionInfo.place}"

    private fun sanitizeTitle(title: String?): String =
        title
            ?.trim()
            ?.take(100)
            ?.takeIf { it.isNotBlank() } ?: "MoneyLog Transaction"

    companion object {
        private const val CHANNEL_ID = "moneylog_channel"
        private const val CHANNEL_NAME = "MoneyLog Notifications"
    }
}
