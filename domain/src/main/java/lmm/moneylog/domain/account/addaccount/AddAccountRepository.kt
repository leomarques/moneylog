package lmm.moneylog.domain.account.addaccount

import lmm.moneylog.domain.account.Account

interface AddAccountRepository {
    suspend fun save(account: Account)
}
