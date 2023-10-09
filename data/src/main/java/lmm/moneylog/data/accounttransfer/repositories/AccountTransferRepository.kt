package lmm.moneylog.data.accounttransfer.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.time.DomainTime

interface AccountTransferRepository {
    suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    )

    fun getTransfers(): Flow<List<AccountTransfer>>
}
