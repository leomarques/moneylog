package lmm.moneylog.data.misc

import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.time.repositories.DomainTimeRepository

fun getInvoicesCodes(currentDomainTime: DomainTime): List<String> {
    val currentCode = currentDomainTime.toInvoiceCode()
    val previousCode = currentCode.previousInvoiceCode()
    val nextCode = currentCode.nextInvoiceCode()

    return listOf(
        previousCode,
        currentCode,
        nextCode
    )
}

fun String.nextInvoiceCode(): String {
    val split = split(".")
    val month = split[0].toInt()

    if (month + 1 > 12) {
        val year = split[1].toInt() + 1
        return "1.$year"
    } else {
        return "${month + 1}.${split[1]}"
    }
}

fun String.previousInvoiceCode(): String {
    val split = split(".")
    val month = split[0].toInt()

    if (month - 1 == 0) {
        val year = split[1].toInt() - 1
        return "12.$year"
    } else {
        return "${month - 1}.${split[1]}"
    }
}

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
