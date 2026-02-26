package lmm.moneylog.ui.features.transfer.list.model

data class TransferModel(
    val value: String,
    val originAccount: String,
    val destinationAccount: String,
    val date: String,
    val id: Int
)
