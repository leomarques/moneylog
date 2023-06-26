package lmm.moneylog.domain.getaccount

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Account

interface GetAccountRepository {

    fun getAccountById(id: Int): Flow<Account?>
}
