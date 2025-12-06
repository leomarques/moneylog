package lmm.moneylog.data.misc

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.database.CreditCardEntity
import lmm.moneylog.data.misc.SampleDataGenerator.ACCOUNT_ID_NUCONTA
import lmm.moneylog.data.misc.SampleDataGenerator.ACCOUNT_ID_SANTANDER
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_FOOD
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_GIFT
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_SALARY
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_TRANSPORT
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_CLOSING_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_DUE_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_ID_NUBANK
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_LIMIT
import lmm.moneylog.data.misc.SampleDataGenerator.DEFAULT_TRANSACTION_COUNT
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import java.time.LocalDate
import java.util.concurrent.Executors

data class TransactionGenerationParams(
    val transactionDao: TransactionDao,
    val accountIds: List<Int>,
    val incomeCategoryIds: List<Int>,
    val expenseCategoryIds: List<Int>,
    val creditCardId: Int,
    val count: Int = DEFAULT_TRANSACTION_COUNT
)

private suspend fun insertAccounts(accountDao: AccountDao) {
    accountDao.insert(
        AccountEntity(
            name = SampleDataGenerator.Accounts.NUCONTA_NAME,
            color = SampleDataGenerator.Accounts.NUCONTA_COLOR,
            archived = false
        )
    )

    accountDao.insert(
        AccountEntity(
            name = SampleDataGenerator.Accounts.SANTANDER_NAME,
            color = SampleDataGenerator.Accounts.SANTANDER_COLOR,
            archived = false
        )
    )
}

private suspend fun insertCategories(categoryDao: CategoryDao) {
    // Insert income categories
    categoryDao.insert(
        CategoryEntity(
            name = SampleDataGenerator.Categories.SALARY_NAME,
            color = SampleDataGenerator.Categories.SALARY_COLOR,
            isIncome = true
        )
    )

    categoryDao.insert(
        CategoryEntity(
            name = SampleDataGenerator.Categories.GIFT_NAME,
            color = SampleDataGenerator.Categories.GIFT_COLOR,
            isIncome = true
        )
    )

    // Insert expense categories
    categoryDao.insert(
        CategoryEntity(
            name = SampleDataGenerator.Categories.FOOD_NAME,
            color = SampleDataGenerator.Categories.FOOD_COLOR,
            isIncome = false
        )
    )

    categoryDao.insert(
        CategoryEntity(
            name = SampleDataGenerator.Categories.TRANSPORT_NAME,
            color = SampleDataGenerator.Categories.TRANSPORT_COLOR,
            isIncome = false
        )
    )
}

private suspend fun insertCreditCard(creditCardDao: CreditCardDao) {
    creditCardDao.insert(
        CreditCardEntity(
            name = SampleDataGenerator.CreditCards.NUBANK_NAME,
            closingDay = CREDIT_CARD_CLOSING_DAY,
            dueDay = CREDIT_CARD_DUE_DAY,
            limit = CREDIT_CARD_LIMIT,
            color = SampleDataGenerator.CreditCards.NUBANK_COLOR
        )
    )
}

suspend fun populateDB(
    accountDao: AccountDao,
    categoryDao: CategoryDao,
    creditCardDao: CreditCardDao,
    transactionDao: TransactionDao,
) {
    insertAccounts(accountDao)
    insertCategories(categoryDao)
    insertCreditCard(creditCardDao)

    // Generate random transactions over the last 3 years
    generateRandomTransactions(
        params =
            TransactionGenerationParams(
                transactionDao = transactionDao,
                accountIds = listOf(ACCOUNT_ID_NUCONTA, ACCOUNT_ID_SANTANDER),
                incomeCategoryIds = listOf(CATEGORY_ID_SALARY, CATEGORY_ID_GIFT),
                expenseCategoryIds = listOf(CATEGORY_ID_FOOD, CATEGORY_ID_TRANSPORT),
                creditCardId = CREDIT_CARD_ID_NUBANK,
                count = DEFAULT_TRANSACTION_COUNT
            )
    )
}

private object TransactionFactory {
    fun createCreditCardTransaction(
        value: Double,
        description: String,
        date: LocalDate,
        categoryId: Int,
        creditCardId: Int
    ) = TransactionEntity(
        value = value,
        description = description,
        day = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        paidDay = null,
        paidMonth = null,
        paidYear = null,
        invoiceMonth = date.monthValue,
        invoiceYear = date.year,
        accountId = null,
        categoryId = categoryId,
        creditCardId = creditCardId
    )

    fun createAccountTransaction(
        value: Double,
        description: String,
        date: LocalDate,
        accountId: Int,
        categoryId: Int
    ) = TransactionEntity(
        value = value,
        description = description,
        day = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        paidDay = date.dayOfMonth,
        paidMonth = date.monthValue,
        paidYear = date.year,
        invoiceMonth = null,
        invoiceYear = null,
        accountId = accountId,
        categoryId = categoryId,
        creditCardId = null
    )
}

suspend fun generateRandomTransactions(params: TransactionGenerationParams) {
    repeat(params.count) {
        val generatedData =
            SampleDataGenerator.generateTransactionData(
                accountIds = params.accountIds,
                incomeCategoryIds = params.incomeCategoryIds,
                expenseCategoryIds = params.expenseCategoryIds,
                creditCardId = params.creditCardId
            )

        val transaction =
            if (generatedData.useCreditCard) {
                TransactionFactory.createCreditCardTransaction(
                    value = generatedData.value,
                    description = generatedData.description,
                    date = generatedData.date,
                    categoryId = generatedData.categoryId,
                    creditCardId = generatedData.creditCardId!!
                )
            } else {
                TransactionFactory.createAccountTransaction(
                    value = generatedData.value,
                    description = generatedData.description,
                    date = generatedData.date,
                    accountId = generatedData.accountId!!,
                    categoryId = generatedData.categoryId
                )
            }

        params.transactionDao.insertAndReturnId(transaction)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun onCreateCallback(context: Context): RoomDatabase.Callback =
    object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Executors.newSingleThreadScheduledExecutor().execute {
                val database = MoneylogDatabase.getInstance(context)
                val accountDao = database.accountDao()
                val categoryDao = database.categoryDao()
                val creditCardDao = database.creditCardDao()
                val transactionDao = database.transactionDao()

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        populateDB(
                            accountDao = accountDao,
                            categoryDao = categoryDao,
                            creditCardDao = creditCardDao,
                            transactionDao = transactionDao
                        )
                    }
                }
            }
        }
    }
