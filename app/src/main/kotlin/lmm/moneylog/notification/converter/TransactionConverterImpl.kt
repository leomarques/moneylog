package lmm.moneylog.notification.converter

import lmm.moneylog.data.creditcard.utils.InvoiceCalculator
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.notification.model.NotificationTransactionInfo

class TransactionConverterImpl(
    private val domainTimeRepository: DomainTimeRepository,
    private val invoiceCalculator: InvoiceCalculator
) : TransactionConverter {
    override suspend fun convert(transactionInfo: NotificationTransactionInfo): Transaction? {
        return try {
            val value =
                parseValue(
                    valueString = transactionInfo.value,
                    currency = transactionInfo.currency
                )
            if (value != null) {
                val currentTime = domainTimeRepository.getCurrentDomainTime()
                val (invoiceMonth, invoiceYear) =
                    if (transactionInfo.creditCardId != null && transactionInfo.creditCardClosingDay != null) {
                        invoiceCalculator.calculateInvoiceMonthAndYear(transactionInfo.creditCardClosingDay)
                    } else {
                        null to null
                    }

                Transaction(
                    value = -value,
                    description = transactionInfo.place,
                    date = currentTime,
                    accountId = null,
                    categoryId = transactionInfo.categoryId,
                    creditCardId = transactionInfo.creditCardId,
                    invoiceMonth = invoiceMonth,
                    invoiceYear = invoiceYear
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
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
