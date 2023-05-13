package lmm.moneylog.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT value FROM `transaction`")
    fun selectAllValues(): Flow<List<Double>>

    @Query("SELECT * FROM `transaction`")
    fun selectAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` where value > 0")
    fun selectIncomeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` where value < 0")
    fun selectOutcomeTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactionEntity: TransactionEntity)
}