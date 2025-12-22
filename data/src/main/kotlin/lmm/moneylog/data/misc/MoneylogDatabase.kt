package lmm.moneylog.data.misc

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
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordDao
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordEntity
import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.database.CreditCardEntity
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        AccountEntity::class,
        CategoryEntity::class,
        AccountTransferEntity::class,
        CreditCardEntity::class,
        CategoryKeywordEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MoneylogDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    abstract fun accountDao(): AccountDao

    abstract fun categoryDao(): CategoryDao

    abstract fun accountTransferDao(): AccountTransferDao

    abstract fun creditCardDao(): CreditCardDao

    abstract fun categoryKeywordDao(): CategoryKeywordDao

    companion object {
        @Volatile
        private var instance: MoneylogDatabase? = null

        fun getInstance(context: Context): MoneylogDatabase =
            instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    MoneylogDatabase::class.java,
                    "moneylog.db"
                ).fallbackToDestructiveMigration(true)
                .addCallback(onCreateCallback(context))
                .build()
    }
}
