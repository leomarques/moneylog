package lmm.moneylog.data.transaction.nubank.parser

import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo

interface NubankTransactionParser {
    fun parseTransactionInfo(text: String): NubankTransactionInfo?
}
