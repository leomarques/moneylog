package lmm.moneylog.data.accounttransfer.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.accounttransfer.database.AccountTransferDao
import lmm.moneylog.data.accounttransfer.database.AccountTransferEntity
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.time.model.DomainTime

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

    override fun getTransfers(): Flow<List<AccountTransfer>> =
        accountTransferDao.selectAll().map { list ->
            list.map { entity ->
                AccountTransfer(
                    id = entity.id,
                    value = entity.value,
                    year = entity.year,
                    month = entity.month,
                    day = entity.day,
                    originAccountId = entity.originAccountId,
                    destinationAccountId = entity.destinationAccountId
                )
            }
        }

    override fun getTransfersByMonthYear(month: Int, year: Int): Flow<List<AccountTransfer>> =
        accountTransferDao.selectByMonthYear(month, year).map { list ->
            list.map { entity ->
                AccountTransfer(
                    id = entity.id,
                    value = entity.value,
                    year = entity.year,
                    month = entity.month,
                    day = entity.day,
                    originAccountId = entity.originAccountId,
                    destinationAccountId = entity.destinationAccountId
                )
            }
        }

    override suspend fun getAllTransfers(): List<AccountTransfer> =
        accountTransferDao
            .selectAll()
            .map { list ->
                list.map { entity ->
                    AccountTransfer(
                        id = entity.id,
                        value = entity.value,
                        year = entity.year,
                        month = entity.month,
                        day = entity.day,
                        originAccountId = entity.originAccountId,
                        destinationAccountId = entity.destinationAccountId
                    )
                }
            }.first()
}
