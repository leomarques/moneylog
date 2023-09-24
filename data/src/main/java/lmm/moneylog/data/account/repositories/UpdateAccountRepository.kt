package lmm.moneylog.data.account.repositories

import lmm.moneylog.data.account.Account

interface UpdateAccountRepository {

    suspend fun update(account: Account)
}
