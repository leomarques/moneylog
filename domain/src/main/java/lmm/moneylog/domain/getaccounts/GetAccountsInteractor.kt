package lmm.moneylog.domain.getaccounts

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Account
class GetAccountsInteractor(private val repository: GetAccountsRepository) {

    fun getAccounts(): Flow<List<Account>> {
        return repository.getAccounts()
    }
}
