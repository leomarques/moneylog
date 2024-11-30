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
import java.util.concurrent.Executors

suspend fun populateDB(
    accountDao: AccountDao,
    categoryDao: CategoryDao,
    creditCardDao: CreditCardDao,
) {
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
    creditCardDao.insert(
        CreditCardEntity(
            name = "Nubank Violeta",
            closingDay = 3,
            dueDay = 10,
            limit = 10000,
            color = -51457814194814976
        )
    )
}

@OptIn(DelicateCoroutinesApi::class)
fun onCreateCallback(context: Context): RoomDatabase.Callback {
    return object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Executors.newSingleThreadScheduledExecutor().execute {
                val accountDao = MoneylogDatabase.getInstance(context).accountDao()
                val categoryDao = MoneylogDatabase.getInstance(context).categoryDao()
                val creditCardDao = MoneylogDatabase.getInstance(context).creditCardDao()

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        populateDB(
                            accountDao = accountDao,
                            categoryDao = categoryDao,
                            creditCardDao = creditCardDao
                        )
                    }
                }
            }
        }
    }
}
