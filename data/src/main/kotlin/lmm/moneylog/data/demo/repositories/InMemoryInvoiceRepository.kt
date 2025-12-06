package lmm.moneylog.data.demo.repositories

import lmm.moneylog.data.invoice.model.Invoice
import lmm.moneylog.data.invoice.repositories.GetInvoicesRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository

class InMemoryInvoiceRepository(
    private val timeRepository: DomainTimeRepository
) : GetInvoicesRepository {
    override fun getInvoices(): List<Invoice> {
        val currentDomainTime = timeRepository.getCurrentDomainTime()
        val month = currentDomainTime.month
        val year = currentDomainTime.year
        val monthName = timeRepository.getMonthName(month).replaceFirstChar(Char::titlecase)

        val currentInvoice =
            Invoice(
                month = month,
                year = year,
                name = "$monthName $year"
            )

        val nextCode = currentInvoice.nextCode()
        val parts = nextCode.split(".")
        val nextMonth = parts[0].toInt()
        val nextYear = parts[1].toInt()
        val nextMonthName = timeRepository.getMonthName(nextMonth).replaceFirstChar(Char::titlecase)

        val nextInvoice =
            Invoice(
                month = nextMonth,
                year = nextYear,
                name = "$nextMonthName $nextYear"
            )

        return listOf(currentInvoice, nextInvoice)
    }
}
