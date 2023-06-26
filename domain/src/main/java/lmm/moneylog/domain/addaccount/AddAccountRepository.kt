package lmm.moneylog.domain.addaccount

import lmm.moneylog.domain.models.Account

interface AddAccountRepository {
    suspend fun save(account: Account)
}
