package lmm.moneylog.notification.helper

import lmm.moneylog.notification.model.NotificationTransactionInfo

interface NotificationDisplayer {
    fun showNotification(
        title: String?,
        transactionInfo: NotificationTransactionInfo,
        transactionId: Long? = null
    )
}
