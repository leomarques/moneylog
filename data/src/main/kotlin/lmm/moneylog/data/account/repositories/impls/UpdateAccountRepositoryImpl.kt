package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository

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
