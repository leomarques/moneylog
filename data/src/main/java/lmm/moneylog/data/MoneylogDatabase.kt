package lmm.moneylog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.accounttransfer.database.AccountTransferDao
import lmm.moneylog.data.accounttransfer.database.AccountTransferEntity
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        AccountEntity::class,
        CategoryEntity::class,
        AccountTransferEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MoneylogDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun accountTransferDao(): AccountTransferDao

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
                MoneylogDatabase::class.java,
                "Transaction.db"
            )
                .build()
    }
}
