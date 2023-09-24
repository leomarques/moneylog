package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.repositories.AddAccountRepository
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider

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
