package lmm.moneylog.ui.features.credithomecard.model

data class CreditHomeCardUIState(
    val total: String = "",
    val invoices: List<String> = emptyList()
)
