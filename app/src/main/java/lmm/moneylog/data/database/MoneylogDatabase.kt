package lmm.moneylog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.data.database.account.AccountEntity
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.data.database.category.CategoryEntity
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.data.database.transaction.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        AccountEntity::class,
        CategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MoneylogDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: MoneylogDatabase? = null

        fun getInstance(context: Context): MoneylogDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoneylogDatabase::class.java, "Transaction.db"
            )
                .build()
    }
}
