package lmm.moneylog.data.account.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.account.Account

interface GetAccountsRepository {
    fun getAccounts(): Flow<List<Account>>
    suspend fun getAccountsSuspend(): List<Account>
}
