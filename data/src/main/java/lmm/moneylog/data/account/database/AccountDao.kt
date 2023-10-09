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

    @Query("SELECT * FROM `account`")
    suspend fun selectAccountsSuspend(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM `account` WHERE id = :id")
    suspend fun selectAccountById(id: Int): AccountEntity?

    @Update
    suspend fun update(accountEntity: AccountEntity)

    @Query("DELETE FROM `account` WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE account SET archived = :archived WHERE id = :id")
    suspend fun updateArchived(id: Int, archived: Boolean)
}
