package lmm.moneylog.data.transaction.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        "SELECT value, month, year FROM `transaction` " +
            "WHERE accountId IN " +
            "(SELECT id FROM `account` " +
            "WHERE archived = :archived) "
    )
    fun selectValuesFromAccounts(archived: Boolean = false): Flow<List<TransactionBalance>>

    @Query(
        "SELECT value FROM `transaction` " +
            "WHERE accountId = :accountId "
    )
    suspend fun selectValuesByAccountId(accountId: Int): List<Double>

    @Query("SELECT * FROM `transaction`")
    fun selectAllTransactions(): Flow<List<TransactionEntity>>

    @Query(
        "SELECT * FROM `transaction` " +
            "WHERE value > 0 "
    )
    fun selectIncomeTransactions(): Flow<List<TransactionEntity>>

    @Query(
        "SELECT * FROM `transaction` " +
            "WHERE value < 0 "
    )
    fun selectOutcomeTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Query(
        "SELECT * FROM `transaction` " +
            "WHERE id = :id"
    )
    suspend fun selectTransactionById(id: Int): TransactionEntity?

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Query(
        "DELETE FROM `transaction` " +
            "WHERE id = :id"
    )
    suspend fun delete(id: Int)
}
