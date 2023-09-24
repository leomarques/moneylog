package lmm.moneylog.data.account.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.account.Account

interface GetAccountRepository {

    fun getAccountById(id: Int): Flow<Account?>
}
