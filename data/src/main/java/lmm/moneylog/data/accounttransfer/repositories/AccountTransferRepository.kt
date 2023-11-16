package lmm.moneylog.data.accounttransfer.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.time.model.DomainTime

interface AccountTransferRepository {
    suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    )

    fun getTransfers(): Flow<List<AccountTransfer>>
}
