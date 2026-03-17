package lmm.moneylog.data.backup.repositories.impls

import java.io.IOException
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.database.AccountTransferDao
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.backup.model.BackupData
import lmm.moneylog.data.backup.repositories.interfaces.BackupRepository
import lmm.moneylog.data.backup.repositories.interfaces.ImportResult
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordDao
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.model.Transaction

@Suppress("TooManyFunctions")
class BackupRepositoryImpl(
    private val getAccountsRepository: GetAccountsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val accountTransferRepository: AccountTransferRepository,
    private val categoryKeywordRepository: CategoryKeywordRepository,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val creditCardDao: CreditCardDao,
    private val accountTransferDao: AccountTransferDao,
    private val categoryKeywordDao: CategoryKeywordDao
) : BackupRepository {

    override suspend fun exportAllData(): BackupData {
        val archivedAccounts = getAccountsRepository.getAccountsSuspend(archived = true)
        val activeAccounts = getAccountsRepository.getAccountsSuspend(archived = false)
        val categories = getCategoriesRepository.getCategoriesSuspend()
        val creditCards = getCreditCardsRepository.getCreditCardsSuspend()
        val transfers = accountTransferRepository.getAllTransfers()
        val keywords = categoryKeywordRepository.getAllKeywordsSuspend()
        val transactions = transactionDao.selectAllTransactionsForBackup().map { it.toDomainModel() }

        return BackupData(
            accounts = archivedAccounts + activeAccounts,
            categories = categories,
            creditCards = creditCards,
            transactions = transactions,
            accountTransfers = transfers,
            categoryKeywords = keywords
        )
    }

    override suspend fun clearAllData() {
        transactionDao.deleteAll()
        accountTransferDao.deleteAll()
        categoryKeywordDao.deleteAll()
        creditCardDao.deleteAll()
        categoryDao.deleteAll()
        accountDao.deleteAll()
    }

    override suspend fun importAllData(data: BackupData, wipeBeforeImport: Boolean): ImportResult {
        return try {
            if (wipeBeforeImport) clearAllData()

            val accountIdMap = importAccounts(data.accounts)
            val categoryIdMap = importCategories(data.categories)
            val creditCardIdMap = importCreditCards(data.creditCards)

            val transactionsImported =
                importTransactions(
                    data.transactions,
                    accountIdMap,
                    categoryIdMap,
                    creditCardIdMap
                )
            val accountTransfersImported = importAccountTransfers(data.accountTransfers, accountIdMap)
            val categoryKeywordsImported = importCategoryKeywords(data.categoryKeywords, categoryIdMap)

            ImportResult.Success(
                accountsImported = data.accounts.size,
                categoriesImported = data.categories.size,
                creditCardsImported = data.creditCards.size,
                transactionsImported = transactionsImported,
                accountTransfersImported = accountTransfersImported,
                categoryKeywordsImported = categoryKeywordsImported
            )
        } catch (error: IOException) {
            ImportResult.Error(error.message ?: "I/O error during import")
        } catch (error: IllegalStateException) {
            ImportResult.Error(error.message ?: "Invalid import state")
        }
    }

    override suspend fun importAllData(data: BackupData): ImportResult = importAllData(data, false)

    private suspend fun importAccounts(accounts: List<Account>): Map<Int, Int> {
        val idMap = mutableMapOf<Int, Int>()

        accounts.forEach { account ->
            val generatedId =
                accountDao.insert(
                    lmm.moneylog.data.account.database.AccountEntity(
                        name = account.name,
                        color = account.color,
                        archived = account.archived
                    )
                ).toInt()

            if (account.id > 0) idMap[account.id] = generatedId
        }

        return idMap
    }

    private suspend fun importCategories(categories: List<Category>): Map<Int, Int> {
        val idMap = mutableMapOf<Int, Int>()

        categories.forEach { category ->
            val generatedId =
                categoryDao.insert(
                    lmm.moneylog.data.category.database.CategoryEntity(
                        name = category.name,
                        color = category.color,
                        isIncome = category.isIncome
                    )
                ).toInt()

            if (category.id > 0) idMap[category.id] = generatedId
        }

        return idMap
    }

    private suspend fun importCreditCards(creditCards: List<CreditCard>): Map<Int, Int> {
        val idMap = mutableMapOf<Int, Int>()

        creditCards.forEach { card ->
            val generatedId =
                creditCardDao.insert(
                    lmm.moneylog.data.creditcard.database.CreditCardEntity(
                        name = card.name,
                        closingDay = card.closingDay,
                        dueDay = card.dueDay,
                        limit = card.limit,
                        color = card.color
                    )
                ).toInt()

            if (card.id > 0) idMap[card.id] = generatedId
        }

        return idMap
    }

    private suspend fun importTransactions(
        transactions: List<Transaction>,
        accountIdMap: Map<Int, Int>,
        categoryIdMap: Map<Int, Int>,
        creditCardIdMap: Map<Int, Int>
    ): Int {
        transactions.forEach { transaction ->
            transactionDao.insertAndReturnId(
                TransactionEntity(
                    value = transaction.value,
                    description = transaction.description,
                    day = transaction.date.day,
                    month = transaction.date.month,
                    year = transaction.date.year,
                    accountId = transaction.accountId?.let { accountIdMap[it] },
                    categoryId = transaction.categoryId?.let { categoryIdMap[it] },
                    creditCardId = transaction.creditCardId?.let { creditCardIdMap[it] },
                    invoiceMonth = transaction.invoiceMonth,
                    invoiceYear = transaction.invoiceYear
                )
            )
        }

        return transactions.size
    }

    private suspend fun importAccountTransfers(
        transfers: List<lmm.moneylog.data.accounttransfer.model.AccountTransfer>,
        accountIdMap: Map<Int, Int>
    ): Int {
        var imported = 0

        transfers.forEach { transfer ->
            val originId = accountIdMap[transfer.originAccountId]
            val destinationId = accountIdMap[transfer.destinationAccountId]

            if (originId != null && destinationId != null) {
                accountTransferRepository.transfer(
                    value = transfer.value,
                    date = DomainTime(day = transfer.day, month = transfer.month, year = transfer.year),
                    originAccountId = originId,
                    destinationAccountId = destinationId
                )
                imported++
            }
        }

        return imported
    }

    private suspend fun importCategoryKeywords(
        keywords: List<lmm.moneylog.data.categorypredictor.model.CategoryKeyword>,
        categoryIdMap: Map<Int, Int>
    ): Int {
        var imported = 0

        keywords.forEach { keyword ->
            val mappedCategoryId = categoryIdMap[keyword.categoryId]
            if (mappedCategoryId != null) {
                categoryKeywordRepository.addKeyword(
                    categoryId = mappedCategoryId,
                    keyword = keyword.keyword
                )
                imported++
            }
        }

        return imported
    }

    private fun TransactionEntity.toDomainModel(): Transaction =
        Transaction(
            id = id,
            value = value,
            description = description,
            date = DomainTime(day = day, month = month, year = year),
            accountId = accountId,
            categoryId = categoryId,
            creditCardId = creditCardId,
            invoiceMonth = invoiceMonth,
            invoiceYear = invoiceYear
        )
}
