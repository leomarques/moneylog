package lmm.moneylog.ui.features.creditcard.homecard.model

import lmm.moneylog.ui.theme.neutralColor

val mockCard =
    CreditCardHomeCardItem(
        id = 0,
        name = "Nubank Violeta",
        color = neutralColor.value.toLong(),
        value = 100.0
    )

val mockCards =
    listOf(
        mockCard,
        mockCard.copy(id = 1, name = "Nubank Azul"),
    )
