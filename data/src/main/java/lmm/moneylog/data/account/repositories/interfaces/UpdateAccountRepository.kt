package lmm.moneylog.data.account.repositories.interfaces

import lmm.moneylog.data.account.repositories.model.Account

interface UpdateAccountRepository {
    suspend fun update(account: Account)
}
