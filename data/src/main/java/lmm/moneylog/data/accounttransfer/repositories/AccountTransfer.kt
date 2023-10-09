package lmm.moneylog.data.accounttransfer.repositories

data class AccountTransfer(
    val value: Double,
    val originAccountId: Int,
    val destinationAccountId: Int
)
