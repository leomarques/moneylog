package lmm.moneylog.data.accounttransfer.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.accounttransfer.AccountTransfer
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

    override fun getTransfers(): Flow<List<AccountTransfer>> {
        return accountTransferDao.selectAll().map { list ->
            list.map { entity ->
                AccountTransfer(
                    value = entity.value,
                    originAccountId = entity.originAccountId,
                    destinationAccountId = entity.destinationAccountId
                )
            }
        }
    }
}
