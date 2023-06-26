package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.data.database.account.AccountEntity
import lmm.moneylog.domain.editaccount.UpdateAccountRepository
import lmm.moneylog.domain.models.Account

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
