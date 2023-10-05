package lmm.moneylog.ui.features.account.getaccounts

import lmm.moneylog.data.account.Account

data class AccountModel(
    val account: Account,
    val balance: String
)
