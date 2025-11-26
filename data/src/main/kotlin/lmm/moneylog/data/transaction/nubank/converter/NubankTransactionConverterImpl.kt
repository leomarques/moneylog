package lmm.moneylog.data.transaction.nubank.converter

import lmm.moneylog.data.creditcard.utils.InvoiceCalculator
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo

class NubankTransactionConverterImpl(
    private val domainTimeRepository: DomainTimeRepository,
    private val invoiceCalculator: InvoiceCalculator
) : NubankTransactionConverter {
    override suspend fun convert(transactionInfo: NubankTransactionInfo): Transaction? {
        val value =
            parseValue(
                valueString = transactionInfo.value,
                currency = transactionInfo.currency
            ) ?: return null

        val currentTime = domainTimeRepository.getCurrentDomainTime()
        val (invoiceMonth, invoiceYear) =
            if (transactionInfo.creditCardId != null && transactionInfo.creditCardClosingDay != null) {
                invoiceCalculator.calculateInvoiceMonthAndYear(transactionInfo.creditCardClosingDay)
            } else {
                null to null
            }

        return Transaction(
            value = -value,
            description = transactionInfo.place,
            date = currentTime,
            accountId = null,
            categoryId = transactionInfo.categoryId,
            creditCardId = transactionInfo.creditCardId,
            invoiceMonth = invoiceMonth,
            invoiceYear = invoiceYear
        )
    }

    private fun parseValue(
        valueString: String,
        currency: String
    ): Double? {
        val cleanValue =
            valueString
                .replace(currency, "")
                .trim()

        return if (cleanValue.contains(",")) {
            cleanValue
                .replace(".", "")
                .replace(",", ".")
                .toDoubleOrNull()
        } else {
            cleanValue.toDoubleOrNull()
        }
    }
}
