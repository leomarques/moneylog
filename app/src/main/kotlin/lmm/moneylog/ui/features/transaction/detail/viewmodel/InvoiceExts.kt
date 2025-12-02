package lmm.moneylog.ui.features.transaction.detail.viewmodel

import lmm.moneylog.data.invoice.repositories.toInvoice
import lmm.moneylog.data.time.repositories.DomainTimeRepository

fun String?.toDisplayInvoice(domainTimeRepository: DomainTimeRepository): String {
    if (this == null) return ""

    val split = split(".")
    val monthName =
        domainTimeRepository.getMonthName(split[0].toInt()).replaceFirstChar(Char::titlecase)
    val year = split[1].toInt()

    return "Fatura: $monthName $year"
}

fun String?.toInvoiceMonth(): Int? {
    if (this == null) return null

    return split(".")[0].toInt()
}

fun String?.toInvoiceYear(): Int? {
    if (this == null) return null

    return split(".")[1].toInt()
}

fun String.previousCode(domainTimeRepository: DomainTimeRepository): String =
    toInvoice(domainTimeRepository).previousCode()

fun String.nextCode(domainTimeRepository: DomainTimeRepository): String = toInvoice(domainTimeRepository).nextCode()
