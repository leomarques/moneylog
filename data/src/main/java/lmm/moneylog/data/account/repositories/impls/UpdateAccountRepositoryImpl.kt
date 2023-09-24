package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.repositories.UpdateAccountRepository
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider

class UpdateAccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) :
    UpdateAccountRepository {

    override suspend fun update(account: Account) {
        withContext(coroutineDispatcherProvider.provide()) {
            accountDao.update(
                with(account) {
                    AccountEntity(
                        name = name,
                        color = color
                    ).also {
                        it.id = id
                    }
                }
            )
        }
    }
}
