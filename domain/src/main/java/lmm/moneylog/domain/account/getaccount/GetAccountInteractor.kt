package lmm.moneylog.domain.account.getaccount

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.account.Account

class GetAccountInteractor(private val getAccountRepository: GetAccountRepository) {

    fun getAccount(id: Int): Flow<Account?> {
        return getAccountRepository.getAccountById(id)
    }
}
