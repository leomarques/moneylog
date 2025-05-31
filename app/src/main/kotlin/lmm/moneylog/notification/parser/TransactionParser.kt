package lmm.moneylog.notification.parser

import lmm.moneylog.notification.model.NotificationTransactionInfo

interface TransactionParser {
    fun parseTransactionInfo(text: String): NotificationTransactionInfo?
}
