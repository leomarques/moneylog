package lmm.moneylog.ui.features.transaction.list.view.layout

import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.theme.outcome

val transactionModelPreview1 =
    TransactionModel(
        value = "R$50,00",
        isIncome = true,
        description = "Calça",
        account = "Nubank",
        category = "Roupa",
        date = "1/2/2023",
        id = 0,
        categoryColor = outcome,
        creditCard = ""
    )

val transactionModelPreview2 =
    TransactionModel(
        value = "R$1,00",
        isIncome = false,
        description = "",
        account = "",
        category = "Roupa",
        date = "22/12/2023",
        id = 0,
        categoryColor = outcome,
        creditCard = "Nubank Violeta"
    )

val transactionModelPreview3 =
    TransactionModel(
        value = "R$1,00",
        isIncome = false,
        description = "",
        account = "Nubank",
        category = "Roupa",
        date = "22/12/2023",
        id = 0,
        categoryColor = outcome,
        creditCard = "Nubank Violeta"
    )

val transactionModelListPreview =
    listOf(
        transactionModelPreview1,
        transactionModelPreview2,
        transactionModelPreview3
    )
