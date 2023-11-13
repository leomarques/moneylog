package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository
import lmm.moneylog.data.account.repositories.model.Account

class UpdateAccountRepositoryImpl(
    private val accountDao: AccountDao
) :
    UpdateAccountRepository {

    override suspend fun update(account: Account) {
        accountDao.update(
            with(account) {
                AccountEntity(
                    name = name,
                    color = color,
                    archived = archived
                ).also {
                    it.id = id
                }
            }
        )
    }
}
