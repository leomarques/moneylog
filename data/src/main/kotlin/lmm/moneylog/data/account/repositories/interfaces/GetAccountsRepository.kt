package lmm.moneylog.data.account.repositories.interfaces

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.account.model.Account

interface GetAccountsRepository {
    fun getAccounts(archived: Boolean = false): Flow<List<Account>>

    suspend fun getAccountsSuspend(archived: Boolean = false): List<Account>

    suspend fun getAccountById(id: Int): Account?
}
