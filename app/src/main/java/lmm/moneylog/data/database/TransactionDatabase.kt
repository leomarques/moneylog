package lmm.moneylog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getInstance(context: Context): TransactionDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TransactionDatabase::class.java, "Transaction.db"
            )
                .build()
    }
}
