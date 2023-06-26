package lmm.moneylog.domain.editaccount

import lmm.moneylog.domain.models.Account

interface UpdateAccountRepository {

    suspend fun update(account: Account)
}
