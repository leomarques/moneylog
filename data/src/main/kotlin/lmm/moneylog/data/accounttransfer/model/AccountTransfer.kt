package lmm.moneylog.data.accounttransfer.model

data class AccountTransfer(
    val id: Int,
    val value: Double,
    val year: Int,
    val month: Int,
    val day: Int,
    val originAccountId: Int,
    val destinationAccountId: Int
)
