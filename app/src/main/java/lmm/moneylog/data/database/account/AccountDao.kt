package lmm.moneylog.data.database.account

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
    fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM `account` where id=:id")
    fun selectAccount(id: Int): Flow<AccountEntity?>

    @Update
    fun update(accountEntity: AccountEntity)

    @Query("DELETE FROM `account` WHERE id = :id")
    fun delete(id: Int)
}
