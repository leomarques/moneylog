package lmm.moneylog.data.accounttransfer

data class AccountTransfer(
    val value: Double,
    val originAccountId: Int,
    val destinationAccountId: Int
)
