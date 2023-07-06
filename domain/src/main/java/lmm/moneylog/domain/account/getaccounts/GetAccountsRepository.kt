package lmm.moneylog.domain.account.getaccounts

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.account.Account

interface GetAccountsRepository {

    fun getAccounts(): Flow<List<Account>>
}
