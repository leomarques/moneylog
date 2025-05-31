package lmm.moneylog.notification.listener

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.notification.config.NotificationConfig
import lmm.moneylog.notification.converter.TransactionConverter
import lmm.moneylog.notification.helper.NotificationDisplayer
import lmm.moneylog.notification.helper.NotificationHelper
import lmm.moneylog.notification.parser.TransactionParser
import org.koin.android.ext.android.inject

class NubankNotificationListener(
    private val transactionParser: TransactionParser? = null,
    private val notificationDisplayer: NotificationDisplayer? = null
) : NotificationListenerService() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val notificationHelper by lazy {
        notificationDisplayer ?: NotificationHelper(this)
    }

    private val addTransactionRepository: AddTransactionRepository by inject()
    private val transactionConverter: TransactionConverter by inject()
    private val injectedTransactionParser: TransactionParser by inject()

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        sbn?.let { notification ->
            if (isSupportedPackage(notification.packageName)) {
                val (title, text) = extractNotificationData(notification)
                processNotificationAsync(title, text)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun isSupportedPackage(packageName: String): Boolean {
        return NotificationConfig.SUPPORTED_PACKAGES.contains(packageName)
    }

    private fun extractNotificationData(notification: StatusBarNotification): Pair<String?, String?> {
        val extras = notification.notification.extras
        val title = sanitizeText(extras.getString("android.title"))
        val text = sanitizeText(extras.getString("android.text"))
        return title to text
    }

    private fun sanitizeText(text: String?): String? {
        return text?.trim()
            ?.takeIf { it.isNotBlank() && it.length <= NotificationConfig.Notification.MAX_TEXT_LENGTH }
    }

    private fun processNotificationAsync(
        title: String?,
        text: String?
    ) {
        scope.launch {
            try {
                text?.let { originalText ->
                    val parser = transactionParser ?: injectedTransactionParser
                    val transactionInfo = parser.parseTransactionInfo(originalText)
                    transactionInfo?.let { info ->
                        val transaction = transactionConverter.convert(info)
                        val transactionId: Long? =
                            transaction?.let { t ->
                                addTransactionRepository.save(t)
                            }

                        withContext(Dispatchers.Main) {
                            notificationHelper.showNotification(
                                title = title,
                                transactionInfo = info,
                                transactionId = transactionId
                            )
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
