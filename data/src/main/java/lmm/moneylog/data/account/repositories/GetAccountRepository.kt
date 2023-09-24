package lmm.moneylog.data.account.repositories

import lmm.moneylog.data.account.Account

interface GetAccountRepository {
    suspend fun getAccountById(id: Int): Account?
}
