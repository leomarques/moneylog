package lmm.moneylog.notification.converter

import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.notification.model.NotificationTransactionInfo

interface TransactionConverter {
    suspend fun convert(transactionInfo: NotificationTransactionInfo): Transaction?
}
