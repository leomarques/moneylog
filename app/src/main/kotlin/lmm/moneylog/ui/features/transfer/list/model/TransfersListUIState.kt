package lmm.moneylog.ui.features.transfer.list.model

data class TransfersListUIState(
    val titleResourceId: Int,
    val monthName: String = "",
    val transfers: List<TransferModel> = emptyList(),
    val total: Double = 0.0
)
