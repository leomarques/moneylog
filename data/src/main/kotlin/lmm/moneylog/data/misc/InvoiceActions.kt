package lmm.moneylog.data.misc

import lmm.moneylog.data.time.repositories.DomainTimeRepository

fun String?.toDisplayInvoice(domainTimeRepository: DomainTimeRepository): String {
    if (this == null) return ""

    val split = split(".")
    val month = domainTimeRepository.getMonthName(split[0].toInt())
    val year = split[1].toInt()

    return "Fatura: $month $year"
}

fun String?.toInvoiceMonth(): Int? {
    if (this == null) return null

    val split = split(".")

    return split[0].toInt()
}

fun String?.toInvoiceYear(): Int? {
    if (this == null) return null

    val split = split(".")

    return split[1].toInt()
}
