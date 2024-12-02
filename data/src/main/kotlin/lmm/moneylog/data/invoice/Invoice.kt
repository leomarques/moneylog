package lmm.moneylog.data.invoice

data class Invoice(
    val year: Int,
    val month: Int,
    val name: String
) {
    fun getCode(): String {
        return "$month.$year"
    }
}
