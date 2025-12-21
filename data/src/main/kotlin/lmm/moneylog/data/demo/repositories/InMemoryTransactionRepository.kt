package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.SearchTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository

class InMemoryTransactionRepository :
    GetTransactionsRepository,
    AddTransactionRepository,
    UpdateTransactionRepository,
    DeleteTransactionRepository,
    SearchTransactionsRepository {
    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())
    private var nextId = 1L

    override suspend fun getTransactionById(id: Long): Transaction? =
        transactions.value.firstOrNull { it.id == id }

    override fun getAllTransactions(month: Int, year: Int): Flow<List<Transaction>> =
        transactions.map { list ->
            list
                .filter {
                    it.date.month == month && it.date.year == year
                }.sortedByDescending { it.date }
        }

    override fun getIncomeTransactions(month: Int, year: Int): Flow<List<Transaction>> =
        transactions.map { list ->
            list
                .filter {
                    it.date.month == month &&
                        it.date.year == year &&
                        it.value > 0
                }.sortedByDescending { it.date }
        }

    override fun getOutcomeTransactions(month: Int, year: Int): Flow<List<Transaction>> =
        transactions.map { list ->
            list
                .filter {
                    it.date.month == month &&
                        it.date.year == year &&
                        it.value < 0
                }.sortedByDescending { it.date }
        }

    override fun getTransactionsByInvoice(
        invoiceCode: String,
        creditCardId: Int
    ): Flow<List<Transaction>> =
        transactions.map { list ->
            val parts = invoiceCode.split("-")
            if (parts.size != 2) {
                emptyList()
            } else {
                val month = parts[0].toIntOrNull() ?: return@map emptyList()
                val year = parts[1].toIntOrNull() ?: return@map emptyList()

                list
                    .filter {
                        it.creditCardId == creditCardId &&
                            it.invoiceMonth == month &&
                            it.invoiceYear == year
                    }.sortedByDescending { it.date }
            }
        }

    override suspend fun save(transaction: Transaction): Long {
        val newTransaction = transaction.copy(id = nextId++)
        transactions.value = transactions.value + newTransaction
        return newTransaction.id
    }

    override suspend fun update(transaction: Transaction) {
        transactions.value =
            transactions.value.map {
                if (it.id == transaction.id) transaction else it
            }
    }

    override suspend fun payInvoice(
        transactions: List<Transaction>,
        paramAccountId: Int,
        paramDate: DomainTime
    ) {
        // Update each transaction in the invoice
        val transactionIds = transactions.map { it.id }.toSet()
        this.transactions.value =
            this.transactions.value.map { existing ->
                if (existing.id in transactionIds) {
                    transactions.first { it.id == existing.id }
                } else {
                    existing
                }
            }

        // Add payment transaction
        val totalValue = transactions.sumOf { it.value }
        val paymentTransaction =
            Transaction(
                id = nextId++,
                value = totalValue,
                description = "Pagamento de fatura",
                date = paramDate,
                accountId = paramAccountId,
                categoryId = null,
                creditCardId = null,
                invoiceMonth = null,
                invoiceYear = null
            )
        this.transactions.value = this.transactions.value + paymentTransaction
    }

    override suspend fun delete(id: Long) {
        transactions.value = transactions.value.filterNot { it.id == id }
    }

    override suspend fun searchByDescription(query: String): List<TransactionSuggestion> {
        val lowerQuery = query.lowercase()
        return transactions.value
            .filter { it.description.lowercase().contains(lowerQuery) }
            .distinctBy { it.description }
            .take(10)
            .map { transaction ->
                TransactionSuggestion(
                    description = transaction.description,
                    value = transaction.value,
                    categoryId = transaction.categoryId,
                    categoryColor = 0xFF000000, // Default color
                    accountId = transaction.accountId,
                    creditCardId = transaction.creditCardId,
                    invoiceMonth = transaction.invoiceMonth,
                    invoiceYear = transaction.invoiceYear
                )
            }
    }

    internal fun clear() {
        transactions.value = emptyList()
        nextId = 1L
    }

    internal fun setInitialData(data: List<Transaction>) {
        transactions.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0L) + 1L
    }

    internal fun getAllTransactionsFlow(): Flow<List<Transaction>> = transactions

    internal fun getAllTransactionsList(): List<Transaction> = transactions.value
}
