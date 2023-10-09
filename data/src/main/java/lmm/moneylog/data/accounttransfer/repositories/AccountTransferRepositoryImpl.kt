package lmm.moneylog.data.accounttransfer.repositories

import lmm.moneylog.data.accounttransfer.database.AccountTransferDao
import lmm.moneylog.data.accounttransfer.database.AccountTransferEntity
import lmm.moneylog.data.transaction.time.DomainTime

class AccountTransferRepositoryImpl(
    private val accountTransferDao: AccountTransferDao
) : AccountTransferRepository {

    override suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    ) {
        accountTransferDao.insert(
            AccountTransferEntity(
                value = value,
                year = date.year,
                month = date.month,
                day = date.day,
                originAccountId = originAccountId,
                destinationAccountId = destinationAccountId
            )
        )
    }

    override suspend fun getTransfers(): List<AccountTransfer> {
        return accountTransferDao.selectAll().map { entity ->
            AccountTransfer(
                value = entity.value,
                originAccountId = entity.originAccountId,
                destinationAccountId = entity.destinationAccountId
            )
        }
    }
}
