package lmm.moneylog.data.creditcard.utils

import lmm.moneylog.data.time.repositories.DomainTimeRepository

class InvoiceCalculator(
    private val domainTimeRepository: DomainTimeRepository
) {
    fun calculateInvoiceForCard(closingDay: Int): String {
        val currentTime = domainTimeRepository.getCurrentDomainTime()
        val currentDay = currentTime.day

        return if (currentDay < closingDay) {
            "${currentTime.month}.${currentTime.year}"
        } else {
            val month = if (currentTime.month == 12) 1 else currentTime.month + 1
            val year = if (currentTime.month == 12) currentTime.year + 1 else currentTime.year
            "$month.$year"
        }
    }

    fun calculateInvoiceMonthAndYear(closingDay: Int): Pair<Int, Int> {
        val currentTime = domainTimeRepository.getCurrentDomainTime()
        val currentDay = currentTime.day

        return if (currentDay < closingDay) {
            currentTime.month to currentTime.year
        } else {
            val month = if (currentTime.month == 12) 1 else currentTime.month + 1
            val year = if (currentTime.month == 12) currentTime.year + 1 else currentTime.year
            month to year
        }
    }
}
