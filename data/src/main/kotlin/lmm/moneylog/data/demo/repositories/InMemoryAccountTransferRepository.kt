package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.time.model.DomainTime

class InMemoryAccountTransferRepository : AccountTransferRepository {
    private val transfers = MutableStateFlow<List<AccountTransfer>>(emptyList())

    override suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    ) {
        val transfer =
            AccountTransfer(
                value = value,
                originAccountId = originAccountId,
                destinationAccountId = destinationAccountId
            )
        transfers.value = transfers.value + transfer
    }

    override fun getTransfers(): Flow<List<AccountTransfer>> = transfers

    internal fun clear() {
        transfers.value = emptyList()
    }

    internal fun setInitialData(data: List<AccountTransfer>) {
        transfers.value = data
    }
}
