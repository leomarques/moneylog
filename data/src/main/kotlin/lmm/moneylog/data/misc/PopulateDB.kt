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
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import java.time.LocalDate
import java.util.concurrent.Executors
import kotlin.random.Random

private const val CREDIT_CARD_CLOSING_DAY = 3
private const val CREDIT_CARD_DUE_DAY = 10
private const val CREDIT_CARD_LIMIT = 10000
private const val YEARS_OF_HISTORY = 3L
private const val DEFAULT_TRANSACTION_COUNT = 500
private const val INCOME_PROBABILITY = 0.3
private const val CREDIT_CARD_PROBABILITY = 0.2
private const val MIN_INCOME = 500.0
private const val MAX_INCOME = 15000.0
private const val MIN_EXPENSE = 10.0
private const val MAX_EXPENSE = 500.0
private const val ACCOUNT_ID_NUCONTA = 1
private const val ACCOUNT_ID_SANTANDER = 2
private const val CATEGORY_ID_SALARY = 1
private const val CATEGORY_ID_GIFT = 2
private const val CATEGORY_ID_FOOD = 3
private const val CATEGORY_ID_TRANSPORT = 4
private const val CREDIT_CARD_ID_NUBANK = 1

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
            name = "NuConta",
            color = -51457814194814976,
            archived = false
        )
    )

    accountDao.insert(
        AccountEntity(
            name = "Santander",
            color = -42442007825612800,
            archived = false
        )
    )
}

private suspend fun insertCategories(categoryDao: CategoryDao) {
    // Insert income categories
    categoryDao.insert(
        CategoryEntity(
            name = "Salário",
            color = -59572381806493696,
            isIncome = true
        )
    )

    categoryDao.insert(
        CategoryEntity(
            name = "Presente",
            color = -60722264810717184,
            isIncome = true
        )
    )

    // Insert expense categories
    categoryDao.insert(
        CategoryEntity(
            name = "Alimentação",
            color = -33386490188791808,
            isIncome = false
        )
    )

    categoryDao.insert(
        CategoryEntity(
            name = "Transporte",
            color = -44970936109105152,
            isIncome = false
        )
    )
}

private suspend fun insertCreditCard(creditCardDao: CreditCardDao) {
    creditCardDao.insert(
        CreditCardEntity(
            name = "Nubank Violeta",
            closingDay = CREDIT_CARD_CLOSING_DAY,
            dueDay = CREDIT_CARD_DUE_DAY,
            limit = CREDIT_CARD_LIMIT,
            color = -51457814194814976
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

private object TransactionDescriptions {
    val income =
        listOf(
            "Salário mensal",
            "Bônus",
            "Freelance",
            "Presente em dinheiro",
            "Reembolso",
            "Venda de item",
            "Renda extra"
        )

    val expense =
        listOf(
            "Supermercado",
            "Restaurante",
            "Lanchonete",
            "Padaria",
            "Café",
            "Uber",
            "Ônibus",
            "Metrô",
            "Gasolina",
            "Estacionamento",
            "Farmácia",
            "Cinema",
            "Streaming",
            "Internet",
            "Água",
            "Energia elétrica",
            "Aluguel",
            "Condomínio",
            "Academia",
            "Livros"
        )
}

private object TransactionGenerator {
    fun generateRandomDate(today: LocalDate, yearsAgo: LocalDate): LocalDate {
        val daysBetween = yearsAgo.toEpochDay().let { today.toEpochDay() - it }
        val randomDays = Random.nextLong(daysBetween + 1)
        return yearsAgo.plusDays(randomDays)
    }

    fun generateValue(isIncome: Boolean): Double =
        if (isIncome) {
            Random.nextDouble(MIN_INCOME, MAX_INCOME)
        } else {
            -Random.nextDouble(MIN_EXPENSE, MAX_EXPENSE)
        }
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
    val today = LocalDate.now()
    val yearsAgo = today.minusYears(YEARS_OF_HISTORY)

    repeat(params.count) {
        val randomDate = TransactionGenerator.generateRandomDate(today, yearsAgo)
        val isIncome = Random.nextDouble() < INCOME_PROBABILITY
        val value = TransactionGenerator.generateValue(isIncome)

        val categoryId =
            if (isIncome) {
                params.incomeCategoryIds.random()
            } else {
                params.expenseCategoryIds.random()
            }

        val description =
            if (isIncome) {
                TransactionDescriptions.income.random()
            } else {
                TransactionDescriptions.expense.random()
            }

        val useCreditCard = !isIncome && Random.nextDouble() < CREDIT_CARD_PROBABILITY

        val transaction =
            if (useCreditCard) {
                TransactionFactory.createCreditCardTransaction(
                    value = value,
                    description = description,
                    date = randomDate,
                    categoryId = categoryId,
                    creditCardId = params.creditCardId
                )
            } else {
                TransactionFactory.createAccountTransaction(
                    value = value,
                    description = description,
                    date = randomDate,
                    accountId = params.accountIds.random(),
                    categoryId = categoryId
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
