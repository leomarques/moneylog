package lmm.moneylog.ui.features.transfer.list.view

import lmm.moneylog.ui.features.transfer.list.model.TransferModel

val transferModelPreview1 =
    TransferModel(
        value = "R$500,00",
        originAccount = "Nubank",
        destinationAccount = "Inter",
        date = "1/2/2023",
        id = 1
    )

val transferModelPreview2 =
    TransferModel(
        value = "R$1.000,00",
        originAccount = "Bradesco",
        destinationAccount = "C6 Bank",
        date = "22/12/2023",
        id = 2
    )

val transferModelPreview3 =
    TransferModel(
        value = "R$250,00",
        originAccount = "Inter",
        destinationAccount = "Nubank",
        date = "22/12/2023",
        id = 3
    )

val transferModelListPreview =
    listOf(
        transferModelPreview1,
        transferModelPreview2,
        transferModelPreview3
    )
