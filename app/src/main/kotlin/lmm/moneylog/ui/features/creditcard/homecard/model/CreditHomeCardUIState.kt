package lmm.moneylog.ui.features.creditcard.homecard.model

data class CreditHomeCardUIState(
    val total: String = "",
    val invoices: List<String> = emptyList()
)
