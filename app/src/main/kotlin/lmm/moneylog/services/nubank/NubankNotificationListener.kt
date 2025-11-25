package lmm.moneylog.services.nubank

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lmm.moneylog.data.transaction.nubank.converter.NubankTransactionConverter
import lmm.moneylog.data.transaction.nubank.parser.NubankTransactionParser
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.ui.features.notification.NotificationDisplayer
import lmm.moneylog.ui.features.notification.NotificationHelper
import org.koin.android.ext.android.inject

class NubankNotificationListener(
    private val transactionParser: NubankTransactionParser? = null,
    private val notificationDisplayer: NotificationDisplayer? = null
) : NotificationListenerService() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val notificationHelper: NotificationDisplayer by lazy {
        notificationDisplayer ?: NotificationHelper(this)
    }

    private val addTransactionRepository: AddTransactionRepository by inject()
    private val transactionConverter: NubankTransactionConverter by inject()
    private val injectedTransactionParser: NubankTransactionParser by inject()

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

    private fun isSupportedPackage(packageName: String): Boolean =
        SUPPORTED_PACKAGES.contains(packageName)

    private fun extractNotificationData(notification: StatusBarNotification): Pair<String?, String?> {
        val extras = notification.notification.extras
        val title = sanitizeText(extras.getString("android.title"))
        val text = sanitizeText(extras.getString("android.text"))
        return title to text
    }

    private fun sanitizeText(text: String?): String? =
        text
            ?.trim()
            ?.takeIf { it.isNotBlank() && it.length <= MAX_TEXT_LENGTH }

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

    companion object {
        private const val NUBANK_PACKAGE = "com.nu.production"
        private const val MAX_TEXT_LENGTH = 500

        private val SUPPORTED_PACKAGES = setOf(NUBANK_PACKAGE)
    }
}
