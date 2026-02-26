package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.time.model.DomainTime

class InMemoryAccountTransferRepository : AccountTransferRepository {
    private val transfers = MutableStateFlow<List<AccountTransfer>>(emptyList())
    private var nextId = 1

    override suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    ) {
        val transfer =
            AccountTransfer(
                id = nextId++,
                value = value,
                year = date.year,
                month = date.month,
                day = date.day,
                originAccountId = originAccountId,
                destinationAccountId = destinationAccountId
            )
        transfers.value += transfer
    }

    override fun getTransfers(): Flow<List<AccountTransfer>> = transfers

    override fun getTransfersByMonthYear(month: Int, year: Int): Flow<List<AccountTransfer>> =
        transfers.map { list ->
            list.filter { it.month == month && it.year == year }
        }

    override suspend fun getAllTransfers(): List<AccountTransfer> = transfers.value

    internal fun setInitialData(data: List<AccountTransfer>) {
        transfers.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
    }

    internal fun clear() {
        transfers.value = emptyList()
        nextId = 1
    }
}
