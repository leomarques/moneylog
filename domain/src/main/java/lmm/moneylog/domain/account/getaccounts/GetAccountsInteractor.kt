package lmm.moneylog.domain.account.getaccounts

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.account.Account
class GetAccountsInteractor(private val repository: GetAccountsRepository) {

    fun getAccounts(): Flow<List<Account>> {
        return repository.getAccounts()
    }
}
