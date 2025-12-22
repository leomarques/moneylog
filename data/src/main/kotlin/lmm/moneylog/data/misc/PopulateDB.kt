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
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_CLOSING_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_DUE_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_LIMIT
import java.util.concurrent.Executors

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
) {
    insertAccounts(accountDao)
    insertCategories(categoryDao)
    insertCreditCard(creditCardDao)
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

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        populateDB(
                            accountDao = accountDao,
                            categoryDao = categoryDao,
                            creditCardDao = creditCardDao,
                        )
                    }
                }
            }
        }
    }
