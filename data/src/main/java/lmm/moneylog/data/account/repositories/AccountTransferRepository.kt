package lmm.moneylog.data.account.repositories

import lmm.moneylog.data.transaction.time.DomainTime

interface AccountTransferRepository {
    suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    )
}
