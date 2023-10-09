package lmm.moneylog.data.account.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.account.Account

interface GetAccountsRepository {
    suspend fun getAccountById(id: Int): Account?
    fun getAccounts(archived: Boolean = false): Flow<List<Account>>
    suspend fun getAccountsSuspend(archived: Boolean = false): List<Account>
}
