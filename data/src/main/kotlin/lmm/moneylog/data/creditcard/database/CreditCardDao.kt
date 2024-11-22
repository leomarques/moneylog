package lmm.moneylog.data.creditcard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao {
    @Query("SELECT * FROM `credit_card`")
    fun selectCreditCards(): Flow<List<CreditCardEntity>>

    @Query("SELECT * FROM `credit_card`")
    suspend fun selectCreditCardsSuspend(): List<CreditCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(creditCardEntity: CreditCardEntity)

    @Query("SELECT * FROM `credit_card` WHERE id = :id")
    suspend fun selectCreditCardById(id: Int): CreditCardEntity?

    @Update
    suspend fun update(creditCardEntity: CreditCardEntity)

    @Query("DELETE FROM `credit_card` WHERE id = :id")
    suspend fun delete(id: Int)
}
