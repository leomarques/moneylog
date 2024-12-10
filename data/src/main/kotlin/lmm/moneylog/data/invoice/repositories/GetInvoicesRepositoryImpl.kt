package lmm.moneylog.data.invoice.repositories

import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.data.time.repositories.DomainTimeRepository

class GetInvoicesRepositoryImpl(private val domainTimeRepository: DomainTimeRepository) :
    GetInvoicesRepository {
    override fun getInvoices(): List<Invoice> {
        val currentDomainTime = domainTimeRepository.getCurrentDomainTime()
        val month = currentDomainTime.month
        val year = currentDomainTime.year
        val monthName = domainTimeRepository.getMonthName(month).replaceFirstChar(Char::titlecase)

        val currentInvoice =
            Invoice(
                month = month,
                year = year,
                name = "$monthName $year"
            )

        return listOf(
            previousInvoice(currentInvoice),
            currentInvoice,
            nextInvoice(currentInvoice)
        )
    }

    private fun previousInvoice(invoice: Invoice): Invoice {
        val year = if (invoice.month == 1) invoice.year - 1 else invoice.year
        val month = if (invoice.month == 1) 12 else invoice.month - 1
        val monthName = domainTimeRepository.getMonthName(month).replaceFirstChar(Char::titlecase)

        return Invoice(
            year = year,
            month = month,
            name = "$monthName $year"
        )
    }

    private fun nextInvoice(invoice: Invoice): Invoice {
        val year = if (invoice.month == 12) invoice.year + 1 else invoice.year
        val month = if (invoice.month == 12) 1 else invoice.month + 1
        val monthName = domainTimeRepository.getMonthName(month).replaceFirstChar(Char::titlecase)

        return Invoice(
            year = year,
            month = month,
            name = "$monthName $year"
        )
    }
}
