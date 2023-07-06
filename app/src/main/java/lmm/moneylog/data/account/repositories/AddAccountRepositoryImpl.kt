package lmm.moneylog.data.account.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.domain.account.Account
import lmm.moneylog.domain.account.addaccount.AddAccountRepository

class AddAccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : AddAccountRepository {

    override suspend fun save(account: Account) {
        withContext(coroutineDispatcherProvider.provide()) {
            accountDao.insert(
                with(account) {
                    AccountEntity(
                        name = name,
                        color = color
                    )
                }
            )
        }
    }
}
