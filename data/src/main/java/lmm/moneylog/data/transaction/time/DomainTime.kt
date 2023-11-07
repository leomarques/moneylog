package lmm.moneylog.data.transaction.time

data class DomainTime(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0
) : Comparable<DomainTime> {
    override fun compareTo(other: DomainTime): Int {
        return if (year != other.year) {
            year.compareTo(other.year)
        } else if (month != other.month) {
            month.compareTo(other.month)
        } else {
            day.compareTo(other.day)
        }
    }
}
