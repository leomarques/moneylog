package lmm.moneylog.domain.time

data class DomainTime(
    val day: Int,
    val month: Int,
    val year: Int
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
