package lmm.moneylog.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun selectAll(): Flow<List<TransactionEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(transactionEntity: TransactionEntity)
}
