package lmm.moneylog.data.transaction.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.balance.model.TransactionBalance

@Dao
interface TransactionDao {
    @Query("SELECT value, month, year, accountId FROM `transaction`")
    fun selectTransactions(): Flow<List<TransactionBalance>>

    @Query("SELECT value FROM `transaction` WHERE accountId = :accountId")
    suspend fun selectValuesByAccountId(accountId: Int): List<Double>

    @Query("SELECT * FROM `transaction`")
    fun selectAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE value > 0 ")
    fun selectIncomeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE value < 0 ")
    fun selectOutcomeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    suspend fun selectTransactionById(id: Int): TransactionEntity?

    @Query("DELETE FROM `transaction` WHERE id = :id")
    suspend fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Update
    suspend fun update(transactionEntityList: List<TransactionEntity>)

    @Query(
        "SELECT * FROM `transaction` " +
            "WHERE invoiceMonth = :invoiceMonth " +
            "AND invoiceYear = :invoiceYear " +
            "AND creditCardId = :creditCardId"
    )
    fun selectTransactionByInvoice(
        invoiceMonth: Int,
        invoiceYear: Int,
        creditCardId: Int
    ): Flow<List<TransactionEntity>>
}
