package lmm.moneylog.ui.features.creditcard.homecard.model

data class CreditHomeCardUIState(
    val cards: List<CreditCardHomeCardItem> = emptyList(),
    val invoiceCode: String = ""
)
