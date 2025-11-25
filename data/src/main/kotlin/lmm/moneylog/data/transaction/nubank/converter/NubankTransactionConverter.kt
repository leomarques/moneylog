package lmm.moneylog.data.transaction.nubank.converter

import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo

interface NubankTransactionConverter {
    suspend fun convert(transactionInfo: NubankTransactionInfo): Transaction?
}
