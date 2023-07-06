package lmm.moneylog.domain.account.editaccount

import lmm.moneylog.domain.account.Account

interface UpdateAccountRepository {

    suspend fun update(account: Account)
}
