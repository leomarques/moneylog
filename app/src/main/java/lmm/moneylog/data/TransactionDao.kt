package lmm.moneylog.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun selectAll(): LiveData<List<TransactionEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(transactionEntity: TransactionEntity)
}
