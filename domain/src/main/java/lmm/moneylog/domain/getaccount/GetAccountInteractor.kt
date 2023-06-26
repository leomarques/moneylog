package lmm.moneylog.domain.getaccount

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Account

class GetAccountInteractor(private val getAccountRepository: GetAccountRepository) {

    fun getAccount(id: Int): Flow<Account?> {
        return getAccountRepository.getAccountById(id)
    }
}
