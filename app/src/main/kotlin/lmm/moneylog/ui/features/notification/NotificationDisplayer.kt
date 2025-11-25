package lmm.moneylog.ui.features.notification

import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo

interface NotificationDisplayer {
    fun showNotification(
        title: String?,
        transactionInfo: NubankTransactionInfo,
        transactionId: Long? = null
    )
}
