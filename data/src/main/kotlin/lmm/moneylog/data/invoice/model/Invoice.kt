package lmm.moneylog.data.invoice.model

data class Invoice(
    val year: Int,
    val month: Int,
    val name: String
) {
    fun getCode(): String {
        return "$month.$year"
    }

    fun previousCode(): String {
        val year = if (month == 1) year - 1 else year
        val month = if (month == 1) 12 else month - 1

        return "$month.$year"
    }

    fun nextCode(): String {
        val year = if (month == 12) year + 1 else year
        val month = if (month == 12) 1 else month + 1

        return "$month.$year"
    }
}
