package lmm.moneylog.data.account.repositories.interfaces

import lmm.moneylog.data.account.repositories.model.Account

interface AddAccountRepository {
    suspend fun save(account: Account)
}
