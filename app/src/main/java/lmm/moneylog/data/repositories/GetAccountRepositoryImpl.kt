package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.domain.getaccount.GetAccountRepository
import lmm.moneylog.domain.models.Account

class GetAccountRepositoryImpl(private val accountDao: AccountDao) :
    GetAccountRepository {

    override fun getAccountById(id: Int): Flow<Account?> {
        return accountDao.selectAccount(id).map { account ->
            if (account != null) {
                with(account) {
                    Account(
                        id = id,
                        name = name,
                        color = color
                    )
                }
            } else {
                null
            }
        }
    }
}
