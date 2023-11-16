package lmm.moneylog.data.accounttransfer.model

data class AccountTransfer(
    val value: Double,
    val originAccountId: Int,
    val destinationAccountId: Int
)
