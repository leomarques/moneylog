package lmm.moneylog.data.account.repositories

import lmm.moneylog.data.account.Account

interface AddAccountRepository {
    suspend fun save(account: Account)
}
