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
import lmm.moneylog.misc.nubank.NubankNotificationActionReceiver
import lmm.moneylog.misc.nubank.NubankNotificationActionReceiver.Companion.ACTION_REMOVE_TRANSACTION
import lmm.moneylog.misc.nubank.NubankNotificationActionReceiver.Companion.EXTRA_NOTIFICATION_ID
import lmm.moneylog.misc.nubank.NubankNotificationActionReceiver.Companion.EXTRA_TRANSACTION_ID
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
                context.getString(R.string.notification_channel_name),
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
                Intent(context, NubankNotificationActionReceiver::class.java).apply {
                    action = ACTION_REMOVE_TRANSACTION
                    putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                    putExtra(EXTRA_TRANSACTION_ID, transactionId)
                }

            val removePendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    removeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            builder.addAction(
                android.R.drawable.ic_menu_delete,
                context.getString(R.string.notification_action_remove),
                removePendingIntent
            )

            val openAppIntent =
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra(EXTRA_TRANSACTION_ID, transactionId)
                }

            val openAppPendingIntent =
                PendingIntent.getActivity(
                    context,
                    notificationId,
                    openAppIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            builder.setContentIntent(openAppPendingIntent)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    private fun formatNotificationText(transactionInfo: NubankTransactionInfo): String =
        context.getString(
            R.string.notification_format_transaction,
            transactionInfo.value,
            transactionInfo.place
        )

    private fun sanitizeTitle(title: String?): String =
        title
            ?.trim()
            ?.take(MAX_TITLE_LENGTH)
            ?.takeIf { it.isNotBlank() } ?: context.getString(R.string.notification_default_title)

    companion object {
        private const val CHANNEL_ID = "moneylog_channel"
        private const val MAX_TITLE_LENGTH = 100
    }
}
