package lmm.moneylog.data.transaction.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
    suspend fun insert(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM `transaction` where id=:id")
    fun selectTransaction(id: Int): Flow<TransactionEntity?>

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Query("DELETE FROM `transaction` WHERE id = :id")
    suspend fun delete(id: Int)
}
