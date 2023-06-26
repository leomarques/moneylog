package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.data.database.account.AccountEntity
import lmm.moneylog.domain.addaccount.AddAccountRepository
import lmm.moneylog.domain.models.Account

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
