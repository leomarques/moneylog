package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.AddAccountRepository

class AddAccountRepositoryImpl(
    private val accountDao: AccountDao
) : AddAccountRepository {

    override suspend fun save(account: Account) {
        accountDao.insert(
            with(account) {
                AccountEntity(
                    name = name,
                    color = color,
                    archived = archived
                )
            }
        )
    }
}
