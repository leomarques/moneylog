package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class InMemoryBalanceRepository(
    private val transactionRepository: InMemoryTransactionRepository
) : GetBalanceRepository {
    override fun getTransactions(): Flow<List<TransactionBalance>> =
        transactionRepository.getAllTransactionsFlow().map { transactions ->
            transactions.map { transaction ->
                TransactionBalance(
                    value = transaction.value,
                    month = transaction.date.month,
                    year = transaction.date.year,
                    accountId = transaction.accountId
                )
            }
        }

    override suspend fun getAllValuesByAccount(accountId: Int): List<Double> =
        transactionRepository
            .getAllTransactionsList()
            .filter { it.accountId == accountId }
            .map { it.value }

    override fun getAllValuesByAccountFlow(accountId: Int): Flow<List<Double>> =
        transactionRepository.getAllTransactionsFlow().map { transactions ->
            transactions
                .filter { it.accountId == accountId }
                .map { it.value }
        }
}
