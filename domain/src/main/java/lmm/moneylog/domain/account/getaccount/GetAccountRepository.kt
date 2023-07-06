package lmm.moneylog.domain.account.getaccount

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.account.Account

interface GetAccountRepository {

    fun getAccountById(id: Int): Flow<Account?>
}
