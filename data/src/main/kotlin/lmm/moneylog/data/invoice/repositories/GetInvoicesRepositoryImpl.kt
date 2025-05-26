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
            currentInvoice,
            currentInvoice.nextCode().toInvoice(domainTimeRepository)
        )
    }
}

fun String.toInvoice(domainTimeRepository: DomainTimeRepository): Invoice {
    val split = split(".")
    val month = split[0].toInt()
    val year = split[1].toInt()

    val monthName = domainTimeRepository.getMonthName(month).replaceFirstChar(Char::titlecase)

    return Invoice(
        month = month,
        year = year,
        name = "$monthName $year"
    )
}
