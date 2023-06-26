package lmm.moneylog.domain.getaccounts

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Account
import lmm.moneylog.domain.models.Transaction

interface GetAccountsRepository {

    fun getAccounts(): Flow<List<Account>>
}
