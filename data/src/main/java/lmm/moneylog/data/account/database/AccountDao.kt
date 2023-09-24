package lmm.moneylog.data.account.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM `account`")
    fun selectAccounts(): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM `account` where id=:id")
    suspend fun selectAccount(id: Int): AccountEntity?

    @Update
    suspend fun update(accountEntity: AccountEntity)

    @Query("DELETE FROM `account` WHERE id = :id")
    suspend fun delete(id: Int)
}
