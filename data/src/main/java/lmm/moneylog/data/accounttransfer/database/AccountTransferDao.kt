package lmm.moneylog.data.accounttransfer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountTransferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountTransferEntity: AccountTransferEntity)

    @Query("SELECT * FROM `account_transfer`")
    suspend fun selectAll(): List<AccountTransferEntity>
}
