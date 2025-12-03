package lmm.moneylog.data.transaction.repositories.impls

import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.data.transaction.repositories.interfaces.SearchTransactionsRepository

class SearchTransactionsRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : SearchTransactionsRepository {
    override suspend fun searchByDescription(query: String): List<TransactionSuggestion> {
        if (query.length < 2) {
            return emptyList()
        }

        val transactions = transactionDao.searchTransactionsByDescription(query)

        // Group by description to get unique descriptions with their most recent transaction
        val uniqueTransactions =
            transactions
                .groupBy { it.description.lowercase() }
                .mapValues { it.value.first() }
                .values

        return uniqueTransactions.map { transaction ->
            val category = transaction.categoryId?.let { categoryDao.selectCategory(it) }
            TransactionSuggestion(
                description = transaction.description,
                value = transaction.value,
                categoryId = transaction.categoryId,
                categoryColor = category?.color ?: 0xFF808080,
                accountId = transaction.accountId,
                creditCardId = transaction.creditCardId,
                invoiceMonth = transaction.invoiceMonth,
                invoiceYear = transaction.invoiceYear
            )
        }
    }
}
